package com.example.demo.draw;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.ConfirmedResponse;
import com.example.demo.SocketDraw.Canvas;
import com.example.demo.guild.Guild;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "drawguild")
public class DrawGuild {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "drawname", nullable = false)
	private String drawname;

	@Column(name = "drawing", nullable = false)
	private String drawing;

	@ManyToOne
	@JoinColumn(name = "Guild_id")
	@JsonIgnore
	private Guild drawHostGuild;

	@OneToMany
	private List<Canvas> canvas;

	public DrawGuild(String drawname, String drawing, Guild drawHostGuild) {
		this.drawname = drawname;
		this.drawing = drawing;
		this.drawHostGuild = drawHostGuild;
		canvas = new ArrayList<Canvas>();
	}
	
	public DrawGuild(String drawname, String drawing) {
		this.drawname = drawname;
		this.drawing = drawing;
		canvas = new ArrayList<Canvas>();
	}

	public DrawGuild() {
		canvas = new ArrayList<Canvas>();
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

	public Guild getDrawHostGuild() {
		return drawHostGuild;
	}

	public void setDrawHostGuild(Guild drawHostGuild) {
		this.drawHostGuild = drawHostGuild;
	}

	public List<Canvas> getCanvas() {
		return canvas;
	}

	public void setCanvas(List<Canvas> canvas) {
		this.canvas = canvas;
	}

	public DrawGuild(long id, String drawname, String drawing, Guild drawHostGuild) {
		this.id = id;
		this.drawname = drawname;
		this.drawing = drawing;
		this.drawHostGuild = drawHostGuild;
		canvas = new ArrayList<Canvas>();
	}

	public ConfirmedResponse ConfirmedDrawGuild(int testCase, long id, String drawname, String drawing,
			Guild drawHostGuild) {
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
			result.setMessage("Error.drawHostGuild.Missmatch");
			Guild g = new Guild();
			setDrawHostGuild(g);
			if ((!(drawHostGuild.equals(this.drawHostGuild))) && (getDrawHostGuild().equals(g))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (5): {
			result.setMessage("Error.canvas.Missmatch");
			List<Canvas> cl = new ArrayList<Canvas>();
			Canvas c = new Canvas("x", drawing,this);
			cl.add(c);
			setCanvas(cl);
			
			if (getCanvas().equals(cl)) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}

		}

		return result;

	}

}
