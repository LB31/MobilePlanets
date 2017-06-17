public interface InteractiveWorld {

	//This will setup the world, initialize a display, prepare OpenGL
	public void setup();
	
	// this will update all internal calculations, get keyboard state, do physics
	// returns TRUE is the world still lives on 
	// returns FALSE to end the main loop
	public boolean update();
	
	//this will render the actual world onto some screen 
	public void draw();
	
	//delete resources, etc. ... maybe do nothing
	public void finish();
	
	//this will be called from external class or main() to run the app-loop
	// could also be called start()
	// in this method all other methods will be 
	// called until some end condition is reached
	public void run();
}