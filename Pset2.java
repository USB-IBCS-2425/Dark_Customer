class Pset2{
	public static void main(String[] args) {
		String name = "Brian Vu";
		String date = "08/09/2024";
		
		System.out.println(name);
		System.out.println(date);
		System.out.println();
		System.out.println();

		



		int x = 100;
		int y = x;
		y = y + 1;
		
		System.out.println(x + " students are in our class");
		System.out.println(y + " students are in our class");
	
		
		if (y < 4) {
			System.out.println("class is canceled");
			}		
		else;{
			System.out.println("unfortunately class will continue");
			}

		

		String firstname = "Brian";
		int namelength = firstname.length();

		x -= namelength;

		System.out.println(x + " students are in our class");

		if (x < 4) {
			System.out.println("class is canceled");
			}		
		else;{
			System.out.println("unfortunately class will continue");
			}



		boolean can = (x % 3 == 0);

		

		if (can){
			System.out.println("The class can be divided into " + (x / 3) + " equal groups.");
		}

		else{
			int cannot = 3 - (x % 3);
			System.out.println("You need to add " + cannot + " more students to divide the class into equal groups of 3.");
		}



		




	}


}