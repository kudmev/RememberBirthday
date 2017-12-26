package dmitrykuznetsov.rememberbirthday.common.support;

/**
 * General type conversion utilities. String -> Long, Integer, etc. and more.
 *
 * @author Leonid Vasilenko
 *         <p/>
 *         DISCLAIMER:
 *         This source id and all accompanying material is (C) 1999-2009 Leonid Vasilenko.
 *         All rights reserved.
 *         <p/>
 *         Any usage of this source id, either in compiled form or in source id form,
 *         redistribution of the source id itself, publication in any media or
 *         inclusion in a library requires the authors expressed written consent.
 *         You may not sale this id for profit.
 *         <p/>
 *         THIS SOFTWARE IS PROVIDED "AS IS" WITHOUT EXPRESS OR IMPLIED WARRANTY.
 *         THE AUTHOR ACCEPTS NO LIABILITY FOR ANY DAMAGE/LOSS OF
 *         BUSINESS THAT THIS PRODUCT MAY CAUSE.
 */
public final class Convert {

    /**
     * Converts any value to long. If unable to convert, zero is returned.
     *
     * @param obj object to convert
     * @return long value
     */
    public long toLong(Object obj) {
        final Long val = toLongObj(obj);
        final long l;
        if (val == null) {
            l = 0;
        } else {
            l = val;
        }
        return l;
    }

    /**
     * Converts any value to Long, return null if unable to convert.
     *
     * @param obj value to convert
     * @return null of Long value
     */
    public Long toLongObj(Object obj) {
        Long l = null;
        if (obj != null) {
            if (obj instanceof Number) {
                l = ((Number) obj).longValue();
            } else if (obj instanceof Boolean) {
                if ((Boolean) obj) {
                    l = 1L;
                } else {
                    l = 0L;
                }
            } else {
                try {
                    l = Long.valueOf(String.valueOf(obj));
                } catch (IllegalArgumentException e) {
                        // OK
                }
            }
        }
        return l;
    }

    /**
     * Converts any value to int. Returns 0 if failed to convert.
     *
     * @param obj value to convert
     * @return integer value
     */
    public int toInt(Object obj) {
        return (int) toLong(obj);
    }

    /**
     * Convert any value to Integer. Returns null if failed to convert.
     *
     * @param obj value to convert
     * @return null or Integer value
     */
    public Integer toIntObj(Object obj) {
        Integer result;
        if (obj == null) {
            result = null;
        } else {
            final Long longVal = toLongObj(obj);
            if (longVal == null) {
                result = null;
            } else {
                result = longVal.intValue();
            }
        }
        return result;
    }

    /**
     * Converts any value to double. Returns 0.0 if failed to convert.
     *
     * @param obj value to convert
     * @return double value
     */
    public double toDouble(Object obj) {
        final Double d = toDoubleObj(obj);
        final double result;
        if (d == null) {
            result = 0.0;
        } else {
            result = d;
        }
        return result;
    }

    /**
     * Converts any value to double. Returns null if failed to convert.
     *
     * @param obj value to convert
     * @return Double value or null
     */
    public Double toDoubleObj(Object obj) {
        Double d = null;
        if (obj != null) {
            if (obj instanceof Number) {
                d = ((Number) obj).doubleValue();
            } else if (obj instanceof Boolean) {
                if ((Boolean) obj) {
                    d = 1.0;
                } else {
                    d = 0.0;
                }
            } else {
                try {
                    d = Double.valueOf(String.valueOf(obj));
                } catch (IllegalArgumentException e) {
                    // OK
                }
            }
        }
        return d;
    }

    /**
     * Converts object to boolean.
     *
     * @param obj value to convert
     * @return <id>boolean</id> value
     */
    public boolean toBoolean(Object obj) {
        boolean b = false;
        if (obj != null) {
            if (obj instanceof Number) {
                b = ((Number) obj).longValue() != 0;
            } else if (obj instanceof Boolean) {
                b = (Boolean) obj;
            } else {
                final String str = String.valueOf(obj);
                b = "true".equalsIgnoreCase(str) || toInt(str) > 0;
            }
        }
        return b;
    }

    /**
     * Converts object to string. Returns null if original value is null.
     *
     * @param obj value to convert
     * @return string value of object, possible <id>null</id>
     */
    public String toString(Object obj) {
        String str = null;
        if (obj != null) {
            str = String.valueOf(obj);
        }
        return str;
    }

    /**
     * Converts object to non-null string. Always returns a string instance, never null.
     *
     * @param obj value to convert
     * @return string object even if specified object is <id>null</id>.
     * Empty string will be returned for <id>null</id>.
     */
    public String toStringObj(Object obj) {
        final String str;
        if (obj == null) {
            str = "";
        } else {
            str = String.valueOf(obj);
        }
        return str;
    }

    /**
     * Performs type conversion to the specified type.
     *
     * @param value value to convert
     * @param type  type to convert to
     * @return value cast to the specified type
     */
    public Object convertType(Object value, Class<?> type) {
        if (value == null && !type.isPrimitive()) {
            return null;
        }

        if (value != null && type.isAssignableFrom(value.getClass())) {
            // No need to convert type
            return value;
        }

        Object result = null;
        if (type.isAssignableFrom(String.class)) {
            result = String.valueOf(value);
        } else if (type.isAssignableFrom(Integer.class)) {
            result = toIntObj(value);
        } else if (type.isAssignableFrom(Integer.TYPE)) {
            result = toInt(value);
        } else if (type.isAssignableFrom(Long.class)) {
            result = toLongObj(value);
        } else if (type.isAssignableFrom(Long.TYPE)) {
            result = toLong(value);
        } else if (type.isAssignableFrom(Boolean.class)) {
            result = toBoolean(value);
        } else if (type.isAssignableFrom(Boolean.TYPE)) {
            result = toBoolean(value);
        }
        return result;
    }

    /**
     * Converts any value to the specified type.
     *
     * @param value value to convert
     * @param type  class of the target type
     * @param <T>   type to convert to
     * @return value of type T
     */
    public <T> T cast(Object value, Class<T> type) {
        final Object result = convertType(value, type);
        return type.cast(result);
    }

}
