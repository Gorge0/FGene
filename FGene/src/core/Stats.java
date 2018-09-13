package core;
import java.io.Serializable;

public class Stats implements Serializable{

	private static final long serialVersionUID = 4L;
	
	public Integer pts = 0;

	public Integer p1st = 0;
	public Integer p2nd = 0;
	public Integer p3rd = 0;
	public Integer p4th = 0;
	public Integer p5th = 0;
	public Integer p6th = 0;
	
	public void zerar(){
		p1st = 0;
		p2nd = 0;
		p3rd = 0;
		p4th = 0;
		p5th = 0;
		p6th = 0;
		pts = 0;
	}
	
	public Integer getTotalRaces(){
		return p1st + p2nd + p3rd + p4th + p5th + p6th;
	}
	
	public void updatePts(){
		pts = p1st*8 + p2nd*5 + p3rd*3 + p4th*2 + p5th*1 + p6th*0;
	}
	
	public boolean isEmpty(){
		if((pts==0)&&(p1st==0)&&(p2nd==0)&&(p3rd==0)&&(p4th==0)&&(p5th==0)&&(p6th==0)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Stats somarStats(Stats st1, Stats st2, boolean isSoma){
		Stats res = new Stats();
		if(st1 == null) {
			st1 = new Stats();
		}
		if(st2 == null) {
			st2 = new Stats();
		}
		if(isSoma){
			res.p1st = st1.p1st + st2.p1st;
			res.p2nd = st1.p2nd + st2.p2nd;
			res.p3rd = st1.p3rd + st2.p3rd;
			res.p4th = st1.p4th + st2.p4th;
			res.p5th = st1.p5th + st2.p5th;
			res.p6th = st1.p6th + st2.p6th;
		}else{
			res.p1st = st1.p1st - st2.p1st;
			res.p2nd = st1.p2nd - st2.p2nd;
			res.p3rd = st1.p3rd - st2.p3rd;
			res.p4th = st1.p4th - st2.p4th;
			res.p5th = st1.p5th - st2.p5th;
			res.p6th = st1.p6th - st2.p6th;
		}
		res.updatePts();
		return res;
	}

	public Integer getPts() {
		return pts;
	}

	public void setPts(Integer pts) {
		this.pts = pts;
	}

	public Integer getP1st() {
		return p1st;
	}

	public void setP1st(Integer p1st) {
		this.p1st = p1st;
	}

	public Integer getP2nd() {
		return p2nd;
	}

	public void setP2nd(Integer p2nd) {
		this.p2nd = p2nd;
	}

	public Integer getP3rd() {
		return p3rd;
	}

	public void setP3rd(Integer p3rd) {
		this.p3rd = p3rd;
	}

	public Integer getP4th() {
		return p4th;
	}

	public void setP4th(Integer p4th) {
		this.p4th = p4th;
	}

	public Integer getP5th() {
		return p5th;
	}

	public void setP5th(Integer p5th) {
		this.p5th = p5th;
	}

	public Integer getP6th() {
		return p6th;
	}

	public void setP6th(Integer p6th) {
		this.p6th = p6th;
	}
	
	
}
