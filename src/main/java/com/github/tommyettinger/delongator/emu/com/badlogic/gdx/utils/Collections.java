
package com.badlogic.gdx.utils;

public class Collections {

	/** When true, {@link Iterable#iterator()} for {@link Array}, {@link ObjectMap}, and other collections will allocate a new
	 * iterator for each invocation. When false, the iterator is reused and nested use will throw an exception. Default is
	 * false. */
	public static boolean allocateIterators;

	public static native int imul(int left, int right)/*-{
	    return Math.imul(left, right);
	}-*/;

	public static native int countLeadingZeros(int n)/*-{
	    return Math.clz32(n);
	}-*/;

	public static native int countTrailingZeros(int n)/*-{
	    var i = -n;
	    return ((n | i) >> 31 | 32) & 31 - Math.clz32(n & i);
	}-*/;

	public static int countLeadingZeros(long n) {
		// we store the top 32 bits first.
		int x = (int)(n >>> 32);
		// if the top 32 bits are 0, we know we don't need to count zeros in them.
		// if they aren't 0, we know there is a 1 bit in there, so we don't need to count the low 32 bits.
		return x == 0 ? 32 + countLeadingZeros((int)n) : countLeadingZeros(x);
	}

	public static int countTrailingZeros(long n) {
		// we store the bottom 32 bits first.
		int x = (int)n;
		// if the bottom 32 bits are 0, we know we don't need to count zeros in them.
		// if they aren't 0, we know there is a 1 bit in there, so we don't need to count the high 32 bits.
		return x == 0 ? 32 + countTrailingZeros((int)(n >>> 32)) : countTrailingZeros(x);
	}

}
