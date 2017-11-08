/*
 * Copyright (C) 2016  BAData Creative Studio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package net.badata.protobuf.converter.utils;

import com.google.protobuf.MessageLite;

import java.lang.reflect.ParameterizedType;

/**
 * Utilities for extract Protobuf messages .
 *
 * @author jsjem
 */
public class MessageUtils {

	/**
	 * Extract Protobuf message type.
	 *
	 * @param object     Object that contains Protobuf message.
	 * @param methodName getter method name.
	 * @return Class of the Protobuf message.
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends MessageLite> getMessageType(final Object object, final String methodName) {
		try {
			return (Class<? extends MessageLite>) object.getClass().getMethod(methodName).getReturnType();
		} catch (NoSuchMethodException e) {
			return MessageLite.class;
		}
	}

	/**
	 * Extract Protobuf message type from collection.
	 *
	 * @param object     Object that contains collection of Protobuf messages.
	 * @param methodName getter method name.
	 * @return Class of the Protobuf message.
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends MessageLite> getMessageCollectionType(final Object object, final String methodName) {
		try {
			ParameterizedType stringListType = (ParameterizedType) object.getClass().getMethod(methodName)
					.getGenericReturnType();
			return (Class<? extends MessageLite>) stringListType.getActualTypeArguments()[0];
		} catch (NoSuchMethodException e) {
			return MessageLite.class;
		}
	}

}
