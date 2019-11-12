package com.cloud.security.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DATASET")
public class MyDataset implements Comparable<MyDataset> {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MYDATASET_ID")
    private List<MyFile> userFiles = new ArrayList<MyFile>();

    @Column(name="MYDATASET_ID")
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
    
    @Column(name="Encryption_Time")
    private int encryption_time;
    
    @Column(name="Weight")
    private int weight;
    
	public List<MyFile> getUserFiles() {
		return userFiles;
	}

	public void setUserFiles(List<MyFile> userFiles) {
		this.userFiles = userFiles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getEncryption_time() {
		return encryption_time;
	}

	public void setEncryption_time(int encryption_time) {
		this.encryption_time = encryption_time;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public MyDataset() {
		super();
	}

	public MyDataset(List<MyFile> userFiles, Integer id, int encryption_time, int weight) {
		super();
		this.userFiles = userFiles;
		this.id = id;
		this.encryption_time = encryption_time;
		this.weight = weight;
	}

	public int compareTo(MyDataset set) {
		System.out.println("dividation: " + this.getWeight() + " : " +((float)((float)this.getWeight() / (float)this.getEncryption_time())));
		return ((float)((float)this.getWeight() / (float)this.getEncryption_time()) < (float)((float)set.getWeight() / (float)set.getEncryption_time()) ? 1 : 
            ((float)(this.getWeight() / (float)this.getEncryption_time()) == (float)((float)set.getWeight() / (float)set.getEncryption_time())? 0 : -1));
	}
}