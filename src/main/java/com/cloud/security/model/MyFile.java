package com.cloud.security.model;

import java.security.PrivateKey;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="USERFILES")
public class MyFile implements Comparable<MyFile> {
	@Column(name="File_Path")
	String filepath;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="Encrypt_time")
	long encTime;
	
	@Column(name="Decrypt_time")
	long decTime;
	
	@Column(name="size")
	long Size;
	
	@Column(name="Encryption_type")
	String enc_type;
	
	public MyFile() {
		super();
	}

	@Id @GeneratedValue
	private Integer id;
	
	@Column(name="Encrypted_Status")
	private boolean encryptedStatus;
	
	@JsonIgnore
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(length=9999999 , name="RSA_Public_Key")
	byte[] aes_Secretkey;
	
	@JsonIgnore
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(length=9999999 , name="RSA_private_Key")
	PrivateKey RSAprivateKey;
	
	@Column(name = "AES_ENC_time")
	long aes_enc_time;
	@Column(name = "RSA_ENC_time")
	long rsa_enc_time;
	@Column(name = "CPABE_ENC_time")
	long cpabe_enc_time;
	@Column(name = "AES_DEC_time")
	long aes_dec_time;
	@Column(name = "RSA_DEC_time")
	long rsa_dec_time;
	@Column(name = "CPABE_DEC_time")
	long cpabe_dec_time;
	
	@Transient
	private String password;

	public boolean isEncryptedStatus() {
		return encryptedStatus;
	}

	public void setEncryptedStatus(boolean encryptedStatus) {
		this.encryptedStatus = encryptedStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getFile_id() {
		return id;
	}

	public void setFile_id(Integer file_id) {
		this.id = file_id;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}


	public long getEncTime() {
		return encTime;
	}

	public long getDecTime() {
		return decTime;
	}

	public void setDecTime(long decTime) {
		this.decTime = decTime;
	}

	public long getSize() {
		return Size;
	}

	public void setSize(long size) {
		Size = size;
	}

	public String getEnc_type() {
		return enc_type;
	}

	public void setEnc_type(String enc_type) {
		this.enc_type = enc_type;
	}

	public byte[] getAes_Secretkey() {
		return aes_Secretkey;
	}

	public void setAes_Secretkey(byte[] aes_Secretkey) {
		this.aes_Secretkey = aes_Secretkey;
	}

	@JsonIgnore
	public PrivateKey getRSAprivateKey() {
		return RSAprivateKey;
	}

	public void setRSAprivateKey(PrivateKey rSAprivateKey) {
		RSAprivateKey = rSAprivateKey;
	}

	public void setEncTime(long encTime) {
		this.encTime = encTime;
	}
	
	
	
	public long getAes_enc_time() {
		return aes_enc_time;
	}

	public void setAes_enc_time(long aes_enc_time) {
		this.aes_enc_time = aes_enc_time;
	}

	public long getRsa_enc_time() {
		return rsa_enc_time;
	}

	public void setRsa_enc_time(long rsa_enc_time) {
		this.rsa_enc_time = rsa_enc_time;
	}

	public long getCpabe_enc_time() {
		return cpabe_enc_time;
	}

	public void setCpabe_enc_time(long cpabe_enc_time) {
		this.cpabe_enc_time = cpabe_enc_time;
	}

	public long getAes_dec_time() {
		return aes_dec_time;
	}

	public void setAes_dec_time(long aes_dec_time) {
		this.aes_dec_time = aes_dec_time;
	}

	public long getRsa_dec_time() {
		return rsa_dec_time;
	}

	public void setRsa_dec_time(long rsa_dec_time) {
		this.rsa_dec_time = rsa_dec_time;
	}

	public long getCpabe_dec_time() {
		return cpabe_dec_time;
	}

	public void setCpabe_dec_time(long cpabe_dec_time) {
		this.cpabe_dec_time = cpabe_dec_time;
	}

	public MyFile(String filepath, long encTime, long decTime, long size, String enc_type, Integer id,
			boolean encryptedStatus, byte[] aes_Secretkey, PrivateKey rSAprivateKey, long aes_enc_time,
			long rsa_enc_time, long cpabe_enc_time, long aes_dec_time, long rsa_dec_time, long cpabe_dec_time,
			String password) {
		super();
		this.filepath = filepath;
		this.encTime = encTime;
		this.decTime = decTime;
		Size = size;
		this.enc_type = enc_type;
		this.id = id;
		this.encryptedStatus = encryptedStatus;
		this.aes_Secretkey = aes_Secretkey;
		RSAprivateKey = rSAprivateKey;
		this.aes_enc_time = aes_enc_time;
		this.rsa_enc_time = rsa_enc_time;
		this.cpabe_enc_time = cpabe_enc_time;
		this.aes_dec_time = aes_dec_time;
		this.rsa_dec_time = rsa_dec_time;
		this.cpabe_dec_time = cpabe_dec_time;
		this.password = password;
	}

	public int compareTo(MyFile file) {
		return (this.getEncTime() < file.getEncTime() ? -1 : 
            (this.getEncTime() == file.getEncTime() ? 0 : 1));  
	}
	
}