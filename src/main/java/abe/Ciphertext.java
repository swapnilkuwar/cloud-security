package abe;

import it.unisa.dia.gas.jpbc.Element;
import abe.serialize.Serializable;
import abe.serialize.SimpleSerializable;

public class Ciphertext implements SimpleSerializable{
	@Serializable
	Policy p;
	@Serializable(group="GT")
	Element Cs; //GT
	@Serializable(group="G1")
	Element C;  //G1
}
