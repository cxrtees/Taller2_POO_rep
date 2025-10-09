package Taller2_POO;

public class PC {
	private String id;
	private String ip;
	private String so;

	public PC(String id, String ip, String so) {
		this.id = id;
		this.ip = ip;
		this.so = so;
	}

	public String getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getSo() {
		return so;
	}

	@Override
	public String toString() {
		return "PC [id=" + id + ", ip=" + ip + ", so=" + so + "]";
	}	
	
}
