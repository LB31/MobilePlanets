import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import edu.berlin.htw.ds.cg.helper.TextureReader;
import edu.berlin.htw.ds.cg.helper.GLDrawHelper;
public class Fork {

	private float pitch = 0;
	private float yaw = 0;
	private float height;
	private float width;
	private Fork leftChild;
	private Fork rightChild;
	private boolean noChildren = false;
	private boolean spinBackPitch = false;
	private boolean spinBackYaw = false;

	// Texture fields
	TextureReader.Texture texture = null;
	int textureID;

	public Fork(float pitch, float yaw, float height, float width, Fork leftChild, Fork rightChild) {
		this.pitch = pitch;
		this.yaw = yaw;
		this.height = height;
		this.width = width;
		this.leftChild = leftChild;
		this.rightChild = rightChild;

	}

	public Fork() {
		noChildren = true;

		try {
			texture = TextureReader.readTexture(getRandomTextures(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, texture.getWidth(), texture.getHeight(), 0, GL11.GL_RGB,
				GL11.GL_UNSIGNED_BYTE, texture.getPixels());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

	}

	public String getRandomTextures() {
		File folder = new File("../CGSS15Ex3MobileDS/dataEx3/Textures");
		File[] listOfFiles = folder.listFiles();

		Random rand = new Random();
		int randNumber = rand.nextInt(listOfFiles.length - 1);
		String pathName = listOfFiles[randNumber].getPath();
		System.out.println(pathName);
		return pathName;
	}

	public void drawFork(boolean left) {
		GL11.glColor3d(0.7, 0.7, 0.7);
		GL11.glLineWidth(5);

		// Change yaw. Range from -80 to 80
		if (yaw > 80)
			spinBackYaw = true;
		else if (yaw <= -80)
			spinBackYaw = false;
		if (spinBackYaw) {
			yaw -= 0.01;
		} else {
			yaw += 0.01;
		}

		if (!noChildren) {

			GL11.glTranslated(0, -height, 0);

			// Pitch
			GL11.glRotatef(pitch, 0, 1, 0);

			GL11.glBegin(GL11.GL_LINES);
			// Vertical line
			GL11.glVertex3d(0, 0, 0);
			GL11.glVertex3d(0, height, 0);
			// Left line
			GL11.glVertex3d(0, 0, 0);
			GL11.glVertex3d(-width / 2, -yaw, 0);
			// Right line
			GL11.glVertex3d(0, 0, 0);
			GL11.glVertex3d(width / 2, yaw, 0);
			GL11.glEnd();

			GL11.glPushMatrix();
			GL11.glTranslated(-width / 2, -yaw, 0);
			leftChild.drawFork(true);

			GL11.glPopMatrix();
			GL11.glTranslated(width / 2, yaw, 0);
			rightChild.drawFork(false);

			// Change pitch. Range from 40 to 500
			if (pitch > 500)
				spinBackPitch = true;
			else if (pitch <= 40)
				spinBackPitch = false;
			if (spinBackPitch) {
				if (left)
					pitch -= 0.01;
				else
					pitch -= 0.02;
			} else {
				if (left)
					pitch += 0.02;
				else
					pitch += 0.01;
			}

		} else {
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

			GLDrawHelper.drawSphere(30, 8, 8);

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			GL11.glDisable(GL11.GL_TEXTURE_2D);

		}

	}

}
