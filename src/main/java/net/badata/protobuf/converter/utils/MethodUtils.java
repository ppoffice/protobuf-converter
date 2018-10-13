package net.badata.protobuf.converter.utils;

import net.badata.protobuf.converter.exception.InvocationException;

import java.lang.reflect.InvocationTargetException;

/**
 * Utilities for reflective method operations.
 *
 * @author ppoffice
 */
public class MethodUtils {

    /**
     * Invoke a method on an object without parameters.
     *
     * @param source	 The object on which the method is to be invoked.
     * @param methodName The name of the method to be invoked.
     * @return Method return value.
     * @throws InvocationException throws when method invocation problem has occurred.
     */
    public static Object invokeMethod(final Object source, final String methodName) throws InvocationException {
        Class<?> sourceClass = source.getClass();
        try {
            return sourceClass.getMethod(methodName).invoke(source);
        } catch (IllegalAccessException e) {
            throw new InvocationException(
                    String.format("Access denied. '%s.%s()'", sourceClass.getName(), methodName));
        } catch (InvocationTargetException e) {
            throw new InvocationException(
                    String.format("Can't invoke method %s() on %s", methodName, sourceClass.getName()));
        } catch (NoSuchMethodException e) {
            throw new InvocationException(
                    String.format("Method not found. '%s.%s()'", sourceClass.getName(), methodName));
        }
    }

    /**
     * Invoke a method on an object with one parameter.
     *
     * @param source	     The object on which the method is to be invoked.
     * @param methodName     The name of the method to be invoked.
     * @param parameterType  The type class of the method parameter.
     * @param parameterValue The value of the method parameter.
     * @return Method return value.
     * @throws InvocationException throws when method invocation problem has occurred.
     */
    public static Object invokeMethod(final Object source, final String methodName, final Class<?> parameterType,
            final Object parameterValue) throws InvocationException {
        Class<?> sourceClass = source.getClass();
        try {
            return sourceClass.getMethod(methodName, parameterType).invoke(source, parameterValue);
        } catch (IllegalAccessException e) {
            throw new InvocationException(
                    String.format("Access denied. '%s.%s(%s)'", sourceClass.getName(), methodName, parameterType.getName()));
        } catch (InvocationTargetException e) {
            throw new InvocationException(
                    String.format("Can't invoke method %s(%s) on %s", methodName, sourceClass.getName(), parameterType.getName()));
        } catch (NoSuchMethodException e) {
            throw new InvocationException(
                    String.format("Method not found. '%s.%s(%s)'", sourceClass.getName(), methodName, parameterType.getName()));
        }
    }
}
