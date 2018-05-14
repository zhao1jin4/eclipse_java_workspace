package spring_cache_map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class MyCache implements Cache 
{
	private String name;

	private Map<String, Account> store = new HashMap<String, Account>();
	public MyCache() {
	}

	public MyCache(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getNativeCache() {
		return store;
	}

	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper result = null;
		Account thevalue = store.get(key);
		if (thevalue != null) {
			result = new SimpleValueWrapper(thevalue);
		}
		return result;
	}

	@Override
	public void put(Object key, Object value) {
		Account thevalue = (Account) value;
		store.put((String) key, thevalue);
	}

	@Override
	public void evict(Object key) {
		store.remove(key);
	}

	@Override
	public void clear() {
		store.clear();
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value=store.get(key);
//		if(value  instanceof  type  )

		T res=(T)store.get(key);
		return res;
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		
		try {
			if(!store.containsKey(key))
				return valueLoader.call();
			else 
				return (T)store.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	
	public ValueWrapper   putIfAbsent( Object key,  Object value) {
		if(store.containsKey(key))
			return new SimpleValueWrapper(store.get(key));
		else
		{
			store.put((String)key, (Account)value);
			return new SimpleValueWrapper(value);
		}
	}
}