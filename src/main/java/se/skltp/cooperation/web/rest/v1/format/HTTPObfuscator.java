/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.cooperation.web.rest.v1.format;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class HTTPObfuscator extends JsonSerializer<String> {

	private static final Object DOTS = "....";

	@Override
	public void serialize(String original, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeObject(obfuscate(original));

	}

	public String obfuscate(String original) {
		// Obfuscate an URL because some people do not want it to be public
		// Specification:
		// before - https://esb.ntjp.sjunet.org:443/adapter/npo/npo/v1
		// after - https://....unet.org....443..../v1

		StringBuffer buffer = new StringBuffer();
		int doubleSlashPosition = original.indexOf("//");
		if (doubleSlashPosition < 0 || original.length() < doubleSlashPosition + 2) {
			return original;
		}
		buffer.append(original.substring(0, doubleSlashPosition + 2));
		buffer.append(DOTS);
		String remainder = original.substring(doubleSlashPosition + 2);
		int semicolonPosition = remainder.indexOf(":");
		int slashPosition = remainder.indexOf("/");
		if (semicolonPosition > 0) {
			buffer.append(remainder.substring(Math.max(semicolonPosition - 6,0), semicolonPosition));
			buffer.append(DOTS);
			if (slashPosition > 0){
				buffer.append(remainder.substring(semicolonPosition + 1, slashPosition));				
			} else{
				buffer.append(remainder.substring(semicolonPosition + 1));								
			}
				
		} else if (slashPosition > 0) {
			buffer.append(remainder.substring(Math.max(slashPosition - 6,0), slashPosition));
		} else {
			buffer.append(remainder.substring(remainder.length() - 6));			
		}
		int lastIndexSlash = remainder.lastIndexOf("/");
		if ( lastIndexSlash > 0 && remainder.length() > lastIndexSlash +1){
			buffer.append(DOTS);
			buffer.append(remainder.substring(lastIndexSlash));			
		}

		return buffer.toString();
	}

}