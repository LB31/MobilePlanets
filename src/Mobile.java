import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Mobile implements InteractiveWorld {
	private boolean endThisApp = false;
	float[] cameraPos = new float[] { 0, 0, 1000 };
	private int width = 1200;
	private int height = 600;
	private float len = 200;
	private float wid = 200;
	private Fork fork;

	public static void main(String[] args) {
		Mobile app = new Mobile();
		app.run();
	}

	public void run() {
		setup();
		while (!endThisApp) {

			update();
			draw();

		}
	}

	public void setup() {

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {

			e.printStackTrace();
			System.exit(0);
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION); // activate matrix to change
		GL11.glLoadIdentity();
		// Parameter: view angle, aspect ratio, far/near -> how far we can see
		GLU.gluPerspective(45.f, width / (float) height, 0.1f, 3000.f);
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Berücksichtigen, wenn ein Objekt
											// hinter einem anderen ist
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Hintergrundfarbe setzen

		fork = new Fork(40, 70, len, wid, new Fork(40, 70, len, wid, new Fork(), new Fork()),
				new Fork(40, 70, len, wid, new Fork(40, 70, len * 0.75f, wid * 0.75f, new Fork(), new Fork()),
						new Fork(40, 70, len * 0.75f, wid * 0.75f, new Fork(), new Fork())));

	}

	public boolean update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Display.isCloseRequested()) {
			endThisApp = true;
		}
		return true;
	}

	public void draw() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // activate modelview; for stuff
												// that isn't in the perspective
		GL11.glLoadIdentity();
		GLU.gluLookAt(cameraPos[0], cameraPos[1], cameraPos[2], 0, -300, 0, 0, 1, 0);

		// GL11.glRotatef(45, 1,1,0);
		fork.drawFork(true);

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {

			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		} else {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
		Display.update();
	}

	public void finish() {
		// TODO Auto-generated method stub

	}

}
