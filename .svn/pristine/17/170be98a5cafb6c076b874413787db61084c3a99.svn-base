/*
 * Copyright 2008 (C) Chunyun Zhao(Chunyun.Zhao@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.jofm;

import java.util.Date;

import net.jofm.annotations.Field;
import net.jofm.annotations.FieldList;
import net.jofm.annotations.Fixed;
import net.jofm.annotations.FixedField;
import net.jofm.annotations.FixedFieldList;
import net.jofm.format.Format.Pad;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Chunyun Zhao
 * @since 1.0
 */
public class FixedMapperTest {

	private FixedMapper fixedMapper;
	
	public FixedMapper getFixedMapper() {
		return fixedMapper;
	}

	public void setFileMapper(FixedMapper fixedMapper) {
		this.fixedMapper = fixedMapper;
	}

	@Before
	public void beforeTest() {
		fixedMapper = new DefaultFixedMapper();
	}

	@Test
	public void shouldMapSimpleFixedData() {
		String line = "POSDHD0000000100000000101230001VA                     quote back                                                  Customer ref        T123456789               Zhao                     Chunyun                                 Sr.10101980M123456789";
		template(line, EquifaxRequest.class);
	}
	@Test
	public void shouldMapSimpleFixedDataWithFixedField() {
		String line = "POSDHD0000000100000000101230001VA                     quote back                                                  Customer ref        T123456789               Zhao                     Chunyun                                 Sr.10101980M123456789";
		line = line + "AD21111     NEChain Bridge              RDMclean              VA2210200017031112222303  ";
		template(line, EquifaxRequest.class);
	}
	@Test
	public void shouldMapSimpleFixedDataWithFixedFields() {
		String line = "POSDHD0000000100000000101230001VA                     quote back                                                  Customer ref        T123456789               Zhao                     Chunyun                                 Sr.10101980M123456789";
		line = line + "AD21111     NEChain Bridge              RDMclean              VA2210200017031112222303  ";
		line = line + "AD32222     SEReston Park               WYReston              VA2019000017031112222303  ";
		template(line, EquifaxRequest.class);
	}
	@Test
	public void shouldMapBlanksToString() {
		String line = "POSDHD0000000100000000101230001VA                     quote back                                                  Customer ref        T123456789               Zhao                     Chunyun                                 Sr.10101980M123456789";
		EquifaxRequest requestObj = (EquifaxRequest)fixedMapper.unmarshall(line, EquifaxRequest.class);
		Assert.assertNull(requestObj.getMvrCode());
		Assert.assertNull(requestObj.getServiceType());
		Assert.assertNull(requestObj.getAccessPin());
	}
	@Test
	public void shouldMapBlanksToDate() {
		String line = "POSDHD0000000100000000101230001VA                     quote back                                                  Customer ref        T123456789               Zhao                     Chunyun                                 Sr.        M123456789";
		EquifaxRequest requestObj = (EquifaxRequest)fixedMapper.unmarshall(line, EquifaxRequest.class);
		Assert.assertNull(requestObj.getBirthDate());
		String mappedBack = fixedMapper.marshall(requestObj);
		Assert.assertEquals(line, mappedBack);
	}
	
	@Test
	public void shouldSupportFormatConfig() {
		String line = "POSDHD0000000100000000101230001VA_______________________________________________________________________quote_back________Customer_ref_______________T123456789_____________________Zhao_____________Chunyun____________________Sr.19801012M123456789";
		DefaultFixedMapper fixedMapper = new DefaultFixedMapper();
		fixedMapper.setPad(Pad.LEFT);
		fixedMapper.setPadWith('_');
		fixedMapper.setDateFormat("yyyyMMdd");
		EquifaxRequest requestObj = (EquifaxRequest)fixedMapper.unmarshall(line, EquifaxRequest.class);
		Assert.assertNotNull(requestObj.getBirthDate());
		String mappedBack = fixedMapper.marshall(requestObj);
		Assert.assertEquals(line.length(), mappedBack.length());
		Assert.assertEquals(line, mappedBack);
	}

	@Test
	public void shouldSupportRequiredValidation() {
		String line = "POSD  0000000100000000101230001VA                     quote back                                                  Customer ref        T123456789               Zhao                     Chunyun                                 Sr.10101980M123456789";
		EquifaxRequest requestObj = (EquifaxRequest)fixedMapper.unmarshall(line, EquifaxRequest.class);
		try {
			fixedMapper.marshall(requestObj);
			Assert.fail("Should throw FixedMappingException.");
		} catch (FixedMappingException fme) {
			Assert.assertTrue(fme.getMessage().contains("is required"));
		}
	}
	
	@Test
	public void shouldThrowExceptionOnInvalidLength() {
		String line = "POSD  0000000100000000101230001VA                     quote back                                                  Customer ref        T123456789               Zhao                     Chunyun                               Sr.10101980M123456789";
		try {
			fixedMapper.unmarshall(line, EquifaxRequest.class);
			Assert.fail("Should throw FixedMappingException.");
		} catch (FixedMappingException fme) {
			Assert.assertTrue(fme.getMessage().contains("must be greater than"));
		}
	}
	
	@Test
	public void shouldFieldsAndEnumFormat() {
		String line = "R00012 Alias1    Alias2    2 1    product 01               2    product 01               ";
		line = line + "AD21111     NEChain Bridge              RDMclean              VA2210200017031112222303  ";
		line = line + "AD32222     SEReston Park               WYReston              VA2019000017031112222303  ";
		line = line + "END";
		
		template(line, Market.class);
	}
	
	public void template(String fixedLengthData, Class<?> fixedClazz) {
		Object requestObj = fixedMapper.unmarshall(fixedLengthData, fixedClazz);
		Assert.assertNotNull(requestObj);
		String mappedBack = fixedMapper.marshall(requestObj);
		System.out.println(mappedBack);
		Assert.assertEquals(fixedLengthData.length(), mappedBack.length());
		Assert.assertEquals(fixedLengthData, mappedBack);		
	}

	@Fixed
	public static class EquifaxRequest {
		@Field(position=1, length=4, required=true)
		private String transactionId;
		@Field(position=2, length=2, required=true)		
		private String recordId;
		@Field(position=3, length=8)		
		private String userId;
		@Field(position=4, length=10, required=true)		
		private String memberNumber;
		@Field(position=5, length=3, required=true)		
		private String securityCode;
		@Field(position=6, length=4, required=true)
		private String customerCode;
		@Field(position=7, length=2)
		private String statePostalCode;
		@Field(position=8, length=1)
		private String mvrCode;
		@Field(position=9, length=9)
		private String serviceType;
		@Field(position=10, length=1)
		private String portabilityIndicator;
		@Field(position=11, length=1)
		private String accessPinBureauIndicator;
		@Field(position=12, length=9)
		private String accessPin;
		@Field(position=13, length=60)
		private String quoteBack;
		@Field(position=14, length=20)
		private String customerRef;
		@Field(position=15, length=25)
		private String driverLicense;
		@Field(position=16, length=25, required=true)
		private String lastName;
		@Field(position=17, length=20, required=true)
		private String firstName;
		@Field(position=18, length=20)
		private String middleName;
		@Field(position=19, length=3)
		private String suffixName;
		@Field(position=20, length=8)
		private Date birthDate;
		@Field(position=21, length=1)
		private String sex;
		@Field(position=22, length=9, required=true)
		private String ssn;
		@FixedField(position=23, identification="AD")
		private Address address;
		@FixedField(position=24, identification="AD")
		private Address formerAddress;		
		
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String getRecordId() {
			return recordId;
		}
		public void setRecordId(String recordId) {
			this.recordId = recordId;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getMemberNumber() {
			return memberNumber;
		}
		public void setMemberNumber(String memberNumber) {
			this.memberNumber = memberNumber;
		}
		public String getSecurityCode() {
			return securityCode;
		}
		public void setSecurityCode(String securityCode) {
			this.securityCode = securityCode;
		}
		public String getCustomerCode() {
			return customerCode;
		}
		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}
		public String getStatePostalCode() {
			return statePostalCode;
		}
		public void setStatePostalCode(String statePostalCode) {
			this.statePostalCode = statePostalCode;
		}
		public String getMvrCode() {
			return mvrCode;
		}
		public void setMvrCode(String mvrCode) {
			this.mvrCode = mvrCode;
		}
		public String getServiceType() {
			return serviceType;
		}
		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}
		public String getPortabilityIndicator() {
			return portabilityIndicator;
		}
		public void setPortabilityIndicator(String portabilityIndicator) {
			this.portabilityIndicator = portabilityIndicator;
		}
		public String getAccessPinBureauIndicator() {
			return accessPinBureauIndicator;
		}
		public void setAccessPinBureauIndicator(String accessPinBureauIndicator) {
			this.accessPinBureauIndicator = accessPinBureauIndicator;
		}
		public String getAccessPin() {
			return accessPin;
		}
		public void setAccessPin(String accessPin) {
			this.accessPin = accessPin;
		}
		public String getQuoteBack() {
			return quoteBack;
		}
		public void setQuoteBack(String quoteBack) {
			this.quoteBack = quoteBack;
		}
		public String getCustomerRef() {
			return customerRef;
		}
		public void setCustomerRef(String customerRef) {
			this.customerRef = customerRef;
		}
		public String getDriverLicense() {
			return driverLicense;
		}
		public void setDriverLicense(String driverLicense) {
			this.driverLicense = driverLicense;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getSuffixName() {
			return suffixName;
		}
		public void setSuffixName(String suffixName) {
			this.suffixName = suffixName;
		}
		public Date getBirthDate() {
			return birthDate;
		}
		public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getSsn() {
			return ssn;
		}
		public void setSsn(String ssn) {
			this.ssn = ssn;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		public Address getFormerAddress() {
			return formerAddress;
		}
		public void setFormerAddress(Address formerAddress) {
			this.formerAddress = formerAddress;
		}
	}
	@Fixed
	public static class Address {
		@Field(position=2, length=10)		
		private String houseNumber;
		@Field(position=3, length=2)		
		private String direction;
		@Field(position=4, length=26)		
		private String streetName;
		@Field(position=5, length=2)		
		private String streetType;
		@Field(position=6, length=20)
		private String city;
		@Field(position=7, length=2)
		private String state;
		@Field(position=8, length=9)
		private String zipCode;
		@Field(position=9, length=10)
		private String phoneNumber;
		@Field(position=10, length=5)
		private String apartmentNumber;
		public String getHouseNumber() {
			return houseNumber;
		}
		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}
		public String getDirection() {
			return direction;
		}
		public void setDirection(String direction) {
			this.direction = direction;
		}
		public String getStreetName() {
			return streetName;
		}
		public void setStreetName(String streetName) {
			this.streetName = streetName;
		}
		public String getStreetType() {
			return streetType;
		}
		public void setStreetType(String streetType) {
			this.streetType = streetType;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZipCode() {
			return zipCode;
		}
		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getApartmentNumber() {
			return apartmentNumber;
		}
		public void setApartmentNumber(String apartmentNumber) {
			this.apartmentNumber = apartmentNumber;
		}
	}	
	
	@Fixed
	public static class Market {
		public static enum ReturnCode {R0001, R0002, R0003, R0004};
		
		@Field(position=1, length=5)
		private ReturnCode returnCode;
		
		@FieldList(position=2, 
				counterField=@Field(length=2, position=0),
				field=@Field(length=10, position=1))
		private String[] aliases;
		
		@FixedFieldList(position=3, 
				counterField=@Field(length=2, position=0),
				fixedField=@FixedField(position=1)) 
		private Product[] products;
		
		@FixedFieldList(position=4, 
				fixedField=@FixedField(identification="AD", position=1)) 
		private Address[] addresses;
		
		@Field(position=5, length=3)
		private String end;

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}

		public ReturnCode getReturnCode() {
			return returnCode;
		}

		public void setReturnCode(ReturnCode returnCode) {
			this.returnCode = returnCode;
		}

		public String[] getAliases() {
			return aliases;
		}

		public void setAliases(String[] aliases) {
			this.aliases = aliases;
		}

		public Product[] getProducts() {
			return products;
		}

		public void setProducts(Product[] products) {
			this.products = products;
		}

		public Address[] getAddresses() {
			return addresses;
		}

		public void setAddresses(Address[] addresses) {
			this.addresses = addresses;
		}
	}
	
	@Fixed 
	public static class Product {
		@Field(position=1, length=5)
		private String productNumber;
		
		@Field(position=2, length=25)
		private String productDescription;

		public String getProductNumber() {
			return productNumber;
		}

		public void setProductNumber(String productNumber) {
			this.productNumber = productNumber;
		}

		public String getProductDescription() {
			return productDescription;
		}

		public void setProductDescription(String productDescription) {
			this.productDescription = productDescription;
		}
	}
}
