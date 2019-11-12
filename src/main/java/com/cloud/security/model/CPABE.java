package com.cloud.security.model;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;

import abe.CPABEImpl;
import abe.Ciphertext;
import abe.MasterKey;
import abe.Parser;
import abe.Policy;
import abe.PublicKey;
import abe.SecretKey;
import abe.crypto.aes.AES;
import abe.serialize.SerializeUtils;
import it.unisa.dia.gas.jpbc.Element;

public class CPABE {
	public static final String Default_PKFileName = "PublicKey";
	public static final String Default_MKFileName = "MasterKey";
	public static final String Default_SKFileName = "SecretKey";
	public static final String Ciphertext_Suffix  = ".cpabe";
	public static final String Error_PK_Missing = "Must set the name of the file that holds the PublicKey!"; 
	public static final String Error_MK_Missing = "Must set the name of the file that holds the MasterKey!"; 
	public static final String Error_SK_Missing = "Must set the name of the file that holds the SecretKey!"; 
	public static final String Error_EncFile_Missing = "Must set the file to be encrypted!";
	public static final String Error_Policy_Missing = "Must set a policy for the file to be encrypted!";
	public static final String Error_Attributes_Missing = "Must set the attributes of the key to be generated!";
	public static final String Error_Ciphertext_Missing = "Must set the name of the file that to be decrypted!";
	public static final String Error_Enc_Directory = "Can not encrypt a directory!";

	public static void main(String[] args) {
		CPABEImpl.debug = true;
		String encFileName = "/home/sudarshan/softwares/tmct8/tmpFiles/again.3gp";
                String ciphertextFileName = "/home/sudarshan/softwares/tmct8/tmpFiles/test.md_enc";
		String PKFileName = "PKFile";
		String MKFileName = "MKFile";
		String SKFileName = "SKFile";
		String policy = "2 of (������ѧ,���ѧԺ,�о���)";
		String[] attrs = new String[]{"������ѧ", "���ѧԺ"};
		setup(PKFileName, MKFileName);
		enc(encFileName, policy, ciphertextFileName, PKFileName);
		keygen(attrs, PKFileName, MKFileName, SKFileName);
		dec(ciphertextFileName, ciphertextFileName, PKFileName, SKFileName);
	}
    
	public static boolean cpabeEncryt(String encFileName,String cipherTextFileName) {
		CPABEImpl.debug = true;
		String PKFileName = "PKFile";
		String MKFileName = "MKFile";
		String SKFileName = "SKFile";
		String policy = "2 of (������ѧ,���ѧԺ,�о���)";
		String[] attrs = new String[]{"������ѧ", "���ѧԺ"};
		setup(PKFileName, MKFileName);
		boolean res = enc(encFileName, policy, cipherTextFileName, PKFileName);
		keygen(attrs, PKFileName, MKFileName, SKFileName);
		return res;
	}

	public static boolean cpabeDecryt(String encFileName,String outFileName) {
		String PKFileName = "PKFile";
		String MKFileName = "MKFile";
		String SKFileName = "SKFile";
		String[] attrs = new String[]{"������ѧ", "���ѧԺ"};
		setup(PKFileName, MKFileName);
		keygen(attrs, PKFileName, MKFileName, SKFileName);
		return dec(encFileName, outFileName, PKFileName, SKFileName);
	}
	
	private static void err(String s){
		System.err.println(s);
	}

	private static boolean isEmptyString(String s){
		return s == null ? true : s.trim().equals("") ? true : false;
	}	

	public static void setup(String PKFileName, String MKFileName){
		PKFileName = isEmptyString(PKFileName) ? Default_PKFileName : PKFileName;
		MKFileName = isEmptyString(MKFileName) ? Default_MKFileName : MKFileName;
		CPABEImpl.setup(PKFileName, MKFileName);
	}
	
	public static boolean enc(String encFileName, String policy, 
			               String outputFileName, String PKFileName){
		if(isEmptyString(encFileName)){
			err(Error_EncFile_Missing);
			return false;
		}
		File encFile = new File(encFileName);
		if(!encFile.exists()){
			err(Error_EncFile_Missing);
			return false;
		}
		if(encFile.isDirectory()){
			err(Error_Enc_Directory);
			return false;
		}
		try {
			outputFileName = isEmptyString(outputFileName) ? 
					encFile.getCanonicalPath() + Ciphertext_Suffix : outputFileName;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if(isEmptyString(policy)){
			err(Error_Policy_Missing);
			return false;
		}
		if(isEmptyString(PKFileName)){
			err(Error_PK_Missing);
			return false;
		}
		PublicKey PK = SerializeUtils.unserialize(PublicKey.class, new File(PKFileName));
		if(PK == null){
			err(Error_PK_Missing);
			return false; 
		}
		Parser parser = new Parser();
		Policy p = parser.parse(policy);
		if(p == null){
			err(Error_Policy_Missing);
			return false;
		}
		boolean res = CPABEImpl.enc(encFile, p, PK, outputFileName);
		encFile.delete();
		return res;
	}
	
	public static void keygen(String[] attrs, String PKFileName, String MKFileName, String SKFileName){
		if(attrs == null || attrs.length == 0){
			err(Error_Attributes_Missing);
			return;
		}
		if(isEmptyString(PKFileName)){
			err(Error_PK_Missing);
			return;
		}
		if(isEmptyString(MKFileName)){
			err(Error_MK_Missing);
			return;
		}
		SKFileName = isEmptyString(SKFileName) ? Default_SKFileName : SKFileName;
		PublicKey PK = SerializeUtils.unserialize(PublicKey.class, new File(PKFileName));
		if(PK == null){
			err(Error_PK_Missing);
			return;
		}
		MasterKey MK = SerializeUtils.unserialize(MasterKey.class, new File(MKFileName));
		CPABEImpl.keygen(attrs, PK, MK, SKFileName);
	}
	
	public static boolean dec(String ciphertextFileName, String outFileName, String PKFileName, String SKFileName){
		if(isEmptyString(ciphertextFileName)){
			err(Error_Ciphertext_Missing);
			return false;
		}
		if(isEmptyString(PKFileName)){
			err(Error_PK_Missing);
			return false;
		}
		if(isEmptyString(SKFileName)){
			err(Error_SK_Missing);
			return false;
		}

		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(new File(ciphertextFileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			 return false;
		}
//		Ciphertext ciphertext = SerializeUtils.unserialize(Ciphertext.class, new File(ciphertextFileName));
		Ciphertext ciphertext = SerializeUtils._unserialize(Ciphertext.class, dis);
		if(ciphertext == null){
			err(Error_Ciphertext_Missing);
			return false;
		}
		PublicKey PK = SerializeUtils.unserialize(PublicKey.class, new File(PKFileName));
		if(PK == null){
			err(Error_PK_Missing);
			return false;
		}
		SecretKey SK = SerializeUtils.unserialize(SecretKey.class, new File(SKFileName));
		if(SK == null){
			err(Error_SK_Missing);
			return false;
		}
		
		String output = null;
		if(ciphertextFileName.endsWith(".cpabe")){
			int end = ciphertextFileName.indexOf(".cpabe");
			output = ciphertextFileName.substring(0, end);
		}
		else{
			output = outFileName;
		}
		File outputFile = CPABEImpl.createNewFile(output);
		OutputStream os = null;
		try {
			os =  new FileOutputStream(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			 return false;
		}
		Element m = CPABEImpl.dec(ciphertext, SK, PK);
		boolean res = AES.crypto(Cipher.DECRYPT_MODE, dis, os, m);
		new File(ciphertextFileName).delete();
		return res;
	}
	
}