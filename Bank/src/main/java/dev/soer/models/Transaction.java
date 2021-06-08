package dev.soer.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Transaction {

	private Integer id;
	private Integer userid;
	private String userAction;
	private Timestamp timestamp;
	
	public Transaction() {
		super();
	}

	public Transaction(Integer id, Integer userid, String userAction, Timestamp timestamp) {
		super();
		this.id = id;
		this.userid = userid;
		this.userAction = userAction;
		this.timestamp = timestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp time) {
		this.timestamp = time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((userAction == null) ? 0 : userAction.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (userAction == null) {
			if (other.userAction != null)
				return false;
		} else if (!userAction.equals(other.userAction))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", userid=" + userid + ", userAction=" + userAction + ", timestamp="
				+ timestamp + "]";
	}

}
