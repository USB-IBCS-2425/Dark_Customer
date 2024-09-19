public class Enclosure{
	String name;
	int numAnimals;
	String animals;
	boolean open;
	public Enclosure(String name){
		this.name = name;
		numAnimals = 0;
		open = false;
		animals = "";

}
	public void openEnclosure(){
		open = true;
}
	public void closeEnclosure(){
		open = false;
}
	public boolean isOpen(){
		return open;
}
	public void addAnimal(String s){
		if(animals==""){
			animals = s;
		}

		else{
			animals = animals +", " + s;
		}
			numAnimals++;
}
	public String getName(){
		return name;
}
	public int getNumberAnimals(){
		return numAnimals;
}
	public String getAnimals(){
		return animals;
}


public static void main(String[] args) {

	Enclosure tigers = new Enclosure ("Tigers");
	Tiger t = new Tiger ("Tony");
	tigers.addAnimal(t.toString());
	System.out.println("The number of tigers is " + tigers.getNumberAnimals());
	System.out.println(tigers.getAnimals());
	t= new Tiger ("Bill");
	tigers.addAnimal(t.toString());
	t = new Tiger ("Terry");
	tigers.addAnimal(t.toString());
	System.out.println("The number of tigers now is " + tigers.getNumberAnimals());
	System.out.println(tigers.getAnimals());
	}
}
