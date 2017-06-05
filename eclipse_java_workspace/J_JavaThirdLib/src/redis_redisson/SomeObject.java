package redis_redisson;

import java.io.Serializable;

public class SomeObject implements Serializable{
		//ÒªÓÐÊôÐÔ
		private int id;
		private String name;

		public SomeObject() {
			this.id=123;
			this.name="name12321";
		}
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}