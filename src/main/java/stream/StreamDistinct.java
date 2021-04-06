package stream;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 내부 개발자 교육을 위한 간단한 java Stream 샘플 소스
 *  - 중복제거
 *
 * @author 엄승하
 */
public class StreamDistinct {

	public static void main(String[] args) {

		//테스트 데이터 생성
		int cnt = 10_000;
		List<TestVO> list = new ArrayList<>(cnt);
		for (int i = 1; i <= cnt; i++) {
			list.add(getTestVO(1, i)); //usreId는 동일하게 생성
		}

		//중복되지 않는 userId 추가 생성
		list.add(getTestVO(2, 10));
		list.add(getTestVO(3, 11));

		long start = System.currentTimeMillis();
		List<String> result = list.stream().filter(distinctByKey(m -> m.getUserId())).map(TestVO::getUserId).collect(Collectors.toList()); //java stream을 이용해서 유니크한 userId리스트만 모으기
		long end = System.currentTimeMillis();

		System.out.println("== Start: 중복 제거된 userId 리스트");

		for (String userId : result) {
			System.out.println(userId);
		}
		System.out.println("== End: 중복 제거된 userId 리스트");

		System.out.println("중복제거 stream filter 소요시간(millis): " + (end - start));

	}

	private static TestVO getTestVO(int suffixUserId, int suffixBookName) {

		TestVO vo = new TestVO();
		vo.setUserId("eom_" + suffixUserId);
		vo.setBookName("book_" + suffixBookName);

		return vo;
	}

	/**
	 * 특정 키로 중복제거
	 *
	 * @param keyExtractor
	 * @param <T>
	 * @return
	 */
	private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new HashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@Data
	private static class TestVO {

		private String userId;
		private String bookName;

	}
}
