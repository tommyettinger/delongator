
package com.badlogic.gdx.utils;

public class Collections {

	/** When true, {@link Iterable#iterator()} for {@link Array}, {@link ObjectMap}, and other collections will allocate a new
	 * iterator for each invocation. When false, the iterator is reused and nested use will throw an exception. Default is
	 * false. */
	public static boolean allocateIterators;

	/**
	 * Multiplies {@code left} and {@code right} using Java's overflow behavior instead of JavaScript's
	 * potentially-precision-losing behavior. Only accessible in super-sourced GWT code.
	 * This uses a method that is present in all recent ECMAScript standards, but not in GWT itself.
	 * @param left any int; will be forced into the 32-bit signed range if needed
	 * @param right any int; will be forced into the 32-bit signed range if needed
	 * @return the product of left and right as a 32-bit signed int
	 */
	public static native int imul(int left, int right)/*-{
	    return Math.imul(left, right);
	}-*/;

	/**
	 * Counts the "leading" zero bits in {@code n}, that is, the bits from the sign bit going from
	 * more-significant to less-significant bits. An input of 0 will return 32.
	 * Equivalent to {@link Integer#numberOfLeadingZeros(int)} on desktop, Android, and iOS platforms.
	 * This uses a method that is present in all recent ECMAScript standards, but not in GWT itself.
	 * Only accessible in super-sourced GWT code.
	 * @param n any int; will be forced into the 32-bit signed range if needed
	 * @return tne number of 0 bits in n starting at the sign bit and going from more to less significant
	 */
	public static native int countLeadingZeros(int n)/*-{
	    return Math.clz32(n);
	}-*/;

	/**
	 * Counts the "trailing" zero bits in {@code n}, that is, the bits from the least-significant bit
	 * going to more-significant bits. An input of 0 will return 32.
	 * Equivalent to {@link Integer#numberOfTrailingZeros(int)} on desktop, Android, and iOS platforms.
	 * This uses a method that is present in all recent ECMAScript standards, but not in GWT itself.
	 * Only accessible in super-sourced GWT code.
	 * @param n any int; will be forced into the 32-bit signed range if needed
	 * @return tne number of 0 bits in n starting at the least significant bit going to more significant
	 */
	public static native int countTrailingZeros(int n)/*-{
	    var i = -n;
	    return ((n | i) >> 31 | 32) & 31 - Math.clz32(n & i);
	}-*/;

	/**
	 * Counts the "leading" zero bits in {@code n}, that is, the bits from the sign bit going from
	 * more-significant to less-significant bits. An input of 0 will return 64.
	 * Equivalent to {@link Long#numberOfLeadingZeros(long)} on desktop, Android, and iOS platforms.
	 * This uses a method that is present in all recent ECMAScript standards, but not in GWT itself.
	 * Only accessible in super-sourced GWT code.
	 * @param n any long
	 * @return tne number of 0 bits in n starting at the sign bit and going from more to less significant
	 */
	public static int countLeadingZeros(long n) {
		// we store the top 32 bits first.
		int x = (int)(n >>> 32);
		// if the top 32 bits are 0, we know we don't need to count zeros in them.
		// if they aren't 0, we know there is a 1 bit in there, so we don't need to count the low 32 bits.
		return x == 0 ? 32 + countLeadingZeros((int)n) : countLeadingZeros(x);
	}

	/**
	 * Counts the "trailing" zero bits in {@code n}, that is, the bits from the least-significant bit
	 * going to more-significant bits. An input of 0 will return 64.
	 * Equivalent to {@link Long#numberOfTrailingZeros(long)} on desktop, Android, and iOS platforms.
	 * This uses a method that is present in all recent ECMAScript standards, but not in GWT itself.
	 * Only accessible in super-sourced GWT code.
	 * @param n any long
	 * @return tne number of 0 bits in n starting at the least significant bit going to more significant
	 */
	public static int countTrailingZeros(long n) {
		// we store the bottom 32 bits first.
		int x = (int)n;
		// if the bottom 32 bits are 0, we know we don't need to count zeros in them.
		// if they aren't 0, we know there is a 1 bit in there, so we don't need to count the high 32 bits.
		return x == 0 ? 32 + countTrailingZeros((int)(n >>> 32)) : countTrailingZeros(x);
	}

}