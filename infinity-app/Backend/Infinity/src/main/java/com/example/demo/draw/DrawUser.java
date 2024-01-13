package com.example.demo.draw;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.ConfirmedResponse;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "drawuser")
public class DrawUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "drawname", nullable = false)
	private String drawname;

	@Column(name = "drawing")
	private String drawing;

	@ManyToOne
	@JoinColumn(name = "User_id")
	@JsonIgnore
	private User drawHostUser;

	
	public DrawUser(String drawname, String drawing, User drawHostUser) {
		this.drawname = drawname;
		this.drawing = drawing;
		this.drawHostUser = drawHostUser;
	}

	public DrawUser(String drawname, String drawing) {
		this.drawname = drawname;
		this.drawing = drawing;
	}

	public DrawUser() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDrawname() {
		return drawname;
	}

	public void setDrawname(String drawname) {
		this.drawname = drawname;
	}

	public String getDrawing() {
		return drawing;
	}

	public void setDrawing(String drawing) {
		this.drawing = drawing;
	}

	public User getDrawHostUser() {
		return drawHostUser;
	}

	public void setDrawHostUser(User drawHostUser) {
		this.drawHostUser = drawHostUser;
	}

	public DrawUser(long id, String drawname, String drawing, User drawHostUser) {
		this.id = id;
		this.drawname = drawname;
		this.drawing = drawing;
		this.drawHostUser = drawHostUser;
	}

	public ConfirmedResponse ConfirmedDrawUser(int testCase, long id, String drawname, String drawing,
			User drawHostUser) {
		ConfirmedResponse result = new ConfirmedResponse();
		result.setApproved(false);
		switch (testCase) {
		case (0): {
			result.setApproved(true);
			return result;
		}
		case (1): {
			result.setMessage("Error.id.Missmatch");
			long inf = Integer.MAX_VALUE;
			setId(inf);
			if ((id != this.id) && (getId() == inf)) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (2): {
			result.setMessage("Error.drawname.Missmatch");
			setDrawname("null");
			if ((!(drawname.equals(this.drawname))) && (getDrawname().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (3): {
			result.setMessage("Error.drawing.Missmatch");
			setDrawing("null");
			if ((!(drawing.equals(this.drawing))) && (getDrawing().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (4): {
			result.setMessage("Error.drawHostUser.Missmatch");
			User u = new User();
			setDrawHostUser(u);
			if ((!(drawHostUser.equals(this.drawHostUser))) && (getDrawHostUser().equals(u))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		}

		return result;
	}

}
