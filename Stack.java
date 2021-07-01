import java.util.Iterator;
import java.util.function.Consumer;

public class Stack<T> implements Iterable<T>
{
	private T[] items;
	private int size;
	
	@SuppressWarnings("unchecked")
	public Stack()	//method to initialize stack array
	{
		items = (T[]) new Object[1];
		size = 0;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	public void push(T x)
	{
		if(size == items.length)
			resize(2*items.length);
		items[size++] = x; 
	}
	
	public T pop()
	{
		T result = items[--size];
		items[size] = null;
		if(size > 0 && size == items.length/4)
			resize(items.length/2);
		return result;
	}
	
	public int size()
	{
		return size; 
	}
	
	public int capacity()
	{
		return items.length;
	}

	private void resize(int capacity)
	{
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[capacity];
		for(int i = 0; i <size; i++)
			temp[i] = items[i];
		items = temp;
	}
	
	public void visit(Consumer<T> action)
	{
		for(int i = size - 1; i >= 0; i--)
			action.accept(items[i]);
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new StackIterator();
	}
	
	
	private class StackIterator implements Iterator<T>
	{
		private int i = size;
		
		@Override
		public boolean hasNext()
		{
			return i > 0;
		}
		
		@Override
		public T next()
		{
			return items[--i];
		}
	}
}
