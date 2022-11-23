import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompositionTest {

	public static void main(String[] args) {
		Map<String, Integer> map = newHashMap();
		List<Integer> list = new ArrayList<>();
		Integer max = max(list);
		System.out.println(max);
		Set<?> s1 = new HashSet<>();
		Set<?> s2 = new HashSet<>();
	}

	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> s = new HashSet<>(s1);
		s.addAll(s2);
		return s;
	}

	public static <K, V> Map<K, V> newHashMap() {
		return new HashMap<>();
	}

	public static <E> Set<E> emptySet() {
		return new HashSet<E>();
	}

	public static <T> T identity(T t) {
		return t;
	}

	public static interface UnaryFunction<T> {

		public static final UnaryFunction<Object> INSTANCE = new UnaryFunction<Object>() {
			@Override
			public Object apply(Object o) {
				return o;
			}
		};

		T apply(T o);
	}

	@SuppressWarnings("unchecked")
	public static <T> UnaryFunction<T> getInstance() {
		return (UnaryFunction<T>) UnaryFunction.INSTANCE;
	}

	public static <T extends Comparable<T>> T max(List<T> list) {
		return list.stream().max(Comparator.naturalOrder()).orElseThrow(RuntimeException::new);
	}


	static class Demo<E> {

	}


}