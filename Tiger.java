class Tiger{
	String type = "Tiger";
	String name;
	int age;
	public Tiger(String name){
	this.name = name;
	}
	public void setAge(int age){
	this.age = age;
	}
	public int getAge(){
		return age;
	}
	public String getName(){
		return name;
	}
	public String gettype(){
		return type;
	}
	public String toString(){
		return name+ " the " + type;
	}

}