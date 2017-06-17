


//original code from http://code.google.com/p/oglsuperbible5/source/browse/trunk/Src/GLTools/src/GLTools.cpp
//Ported to Java by D.Strippgen 2012

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GLDrawHelper {
	
	//Draw a Star in Unit circle
	public static void drawStar(int p, int q){
		double step = 2.*Math.PI / p;
		int pos = 0;
		GL11.glBegin(GL11.GL_LINES);
		for(int i = 0; i<p; i++){
			//Draw line from pos to pos+q
			GL11.glVertex3d(Math.sin(step*pos), Math.cos(step*pos),0);
			pos += q;
			GL11.glVertex3d(Math.sin(step*pos), Math.cos(step*pos),0);
		}
		GL11.glEnd();
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////
	// Make a sphere
	public static void drawSphere(float fRadius, int iSlices, int iStacks)
	        {
	    float drho = (float)(3.141592653589) / (float) iStacks;
	    float dtheta = 2.0f * (float)(3.141592653589) / (float) iSlices;
	        float ds = 1.0f / (float) iSlices;
	        float dt = 1.0f / (float) iStacks;
	        float t = 1.0f;       
	        float s = 0.0f;
	    int j, k;     // Looping variables

	    GL11.glBegin(GL11.GL_TRIANGLES);
	        for (k = 0; k < iStacks; k++) 
	                {
	                float rho = (float)k * drho;
	                float srho = (float)(Math.sin(rho));
	                float crho = (float)(Math.cos(rho));
	                float srhodrho = (float)(Math.sin(rho + drho));
	                float crhodrho = (float)(Math.cos(rho + drho));
	                
	        // Many sources of OpenGL sphere drawing code uses a triangle fan
	        // for the caps of the sphere. This however introduces texturing 
	        // artifacts at the poles on some OpenGL implementations
	        s = 0.0f;
         float[][] vVertex = new float [4][3] ;
         float[][] vNormal = new float [4][3] ;
         float[][] vTexture = new float [4][3] ;

	                for ( j = 0; j < iSlices; j++) 
	                        {
	                        float theta = (j == iSlices) ? 0.0f : j * dtheta;
	                        float stheta = (float)(-Math.sin(theta));
	                        float ctheta = (float)(Math.cos(theta));
	                        
	                        float x = stheta * srho;
	                        float z = ctheta * srho;
	                        float y = crho;
	        
	                        vTexture[0][0] = s;
	                        vTexture[0][1] = t;
	                        vNormal[0][0] = x;
	                        vNormal[0][1] = y;
	                        vNormal[0][2] = z;
	                        vVertex[0][0] = x * fRadius;
	                        vVertex[0][1] = y * fRadius;
	                        vVertex[0][2] = z * fRadius;
	                        
	            x = stheta * srhodrho;
	                        z = ctheta * srhodrho;
	                        y = crhodrho;

	                        vTexture[1][0] = s;
	                        vTexture[1][1] = t - dt;
	                        vNormal[1][0] = x;
	                        vNormal[1][1] = y;
	                        vNormal[1][2] = z;
	                        vVertex[1][0] = x * fRadius;
	                        vVertex[1][1] = y * fRadius;
	                        vVertex[1][2] = z * fRadius;
	                        

	                        theta = ((j+1) == iSlices) ? 0.0f : (j+1) * dtheta;
	                        stheta = (float)(-Math.sin(theta));
	                        ctheta = (float)(Math.cos(theta));
	                        
	                        x = stheta * srho;
	                        z = ctheta * srho;
	                        y = crho;
	        
	            s += ds;
	                        vTexture[2][0] = s;
	                        vTexture[2][1] = t;
	                        vNormal[2][0] = x;
	                        vNormal[2][1] = y;
	                        vNormal[2][2] = z;
	                        vVertex[2][0] = x * fRadius;
	                        vVertex[2][1] = y * fRadius;
	                        vVertex[2][2] = z * fRadius;
	                        
	            x = stheta * srhodrho;
	                        z = ctheta * srhodrho;
	                        y = crhodrho;

	                        vTexture[3][0] = s;
	                        vTexture[3][1] = t - dt;
	                        vNormal[3][0] = x;
	                        vNormal[3][1] = y;
	                        vNormal[3][2] = z;
	                        vVertex[3][0] = x * fRadius;
	                        vVertex[3][1] = y * fRadius;
	                        vVertex[3][2] = z * fRadius;
	                
	                        GL11.glNormal3f(vNormal[0][0], vNormal[0][1],vNormal[0][2]);
	                        GL11.glVertex3f(vVertex[0][0], vVertex[0][1],vVertex[0][2]);
	                        GL11.glNormal3f(vNormal[1][0], vNormal[1][1],vNormal[1][2]);
	                		GL11.glVertex3f(vVertex[1][0], vVertex[1][1],vVertex[1][2]);
	                        GL11.glNormal3f(vNormal[2][0], vNormal[2][1],vNormal[2][2]);
	                		GL11.glVertex3f(vVertex[2][0], vVertex[2][1],vVertex[2][2]);

	                		for(int i=0;i<3;i++){vVertex[0][i] =vVertex[1][i];vNormal[0][i] =vNormal[1][i];vTexture[0][i] =vTexture[1][i];}
	                		for( int   i=0;i<3;i++){vVertex[1][i] =vVertex[3][i];vNormal[1][i] =vNormal[3][i];vTexture[1][i] =vTexture[3][i];}

	                        GL11.glNormal3f(vNormal[0][0], vNormal[0][1],vNormal[0][2]);
	                        GL11.glVertex3f(vVertex[0][0], vVertex[0][1],vVertex[0][2]);
	                        GL11.glNormal3f(vNormal[1][0], vNormal[1][1],vNormal[1][2]);
	                		GL11.glVertex3f(vVertex[1][0], vVertex[1][1],vVertex[1][2]);
	                        GL11.glNormal3f(vNormal[2][0], vNormal[2][1],vNormal[2][2]);
	                		GL11.glVertex3f(vVertex[2][0], vVertex[2][1],vVertex[2][2]);
	                        }
	        t -= dt;
	        }

	        GL11.glEnd();
	    }
	    

	public static void drawDisk(float radius, int nSlices, int nStacks) {
		drawOpenDisk(0, radius, nSlices, nStacks);
	}
	public static void drawOpenDisk(float innerRadius, float outerRadius, int nSlices, int nStacks) {
	        // How much to step out each stack
	        float fStepSizeRadial = outerRadius - innerRadius;
	        if(fStepSizeRadial < 0.0f)                      // Dum dum...
	                fStepSizeRadial *= -1.0f;

	        fStepSizeRadial /= (float)(nStacks);
	        
	        float fStepSizeSlice = (3.1415926536f * 2.0f) / (float)(nSlices);
	        
	        Vector3f vVertex[] = new Vector3f[4];
	        Vector3f vNormal[] = new Vector3f[4];
	        Vector2f vTexture[] = new Vector2f[4];
	        
	        float fRadialScale = 1.0f / outerRadius;
	       
	        GL11.glBegin(GL11.GL_TRIANGLES);
	        for(int i = 0; i < nStacks; i++)                      // Stacks
	                {
	                float theyta;
	                float theytaNext;
	                for(int j = 0; j < nSlices; j++)     // Slices
	                        {
	                        float inner = innerRadius + ((float)(i)) * fStepSizeRadial;
	                        float outer = innerRadius + ((float)(i+1)) * fStepSizeRadial;
	                        
	                        theyta = fStepSizeSlice * (float)(j);
	                        if(j == (nSlices - 1))
	                                theytaNext = 0.0f;
	                        else
	                                theytaNext = fStepSizeSlice * ((float)(j+1));
	                                
	                        vVertex[0] = new Vector3f();
	                        vNormal[0] = new Vector3f();
	                        vTexture[0] = new Vector2f();

	                        // Inner First
	                        vVertex[0].x = (float)Math.cos(theyta) * inner;    // X    
	                        vVertex[0].y = (float)Math.sin(theyta) * inner;    // Y
	                        vVertex[0].z = 0.0f;                                   // Z
	                        
	                        vNormal[0].x = 0.0f;                                   // Surface Normal, same for everybody
	                        vNormal[0].y = 0.0f;
	                        vNormal[0].z = 1.0f;
	                        
	                        vTexture[0].x = ((vVertex[0].x * fRadialScale) + 1.0f) * 0.5f;        
	                        vTexture[0].y = ((vVertex[0].y * fRadialScale) + 1.0f) * 0.5f;
	                        
	                        // Outer First
	                        vVertex[1] = new Vector3f();
	                        vNormal[1] = new Vector3f();
	                        vTexture[1] = new Vector2f();
	                        vVertex[1].x = (float)Math.cos(theyta) * outer;    // X    
	                        vVertex[1].y = (float)Math.sin(theyta) * outer;    // Y
	                        vVertex[1].z = 0.0f;                                   // Z
	                        
	                        vNormal[1].x = 0.0f;                                   // Surface Normal, same for everybody
	                        vNormal[1].y = 0.0f;
	                        vNormal[1].z = 1.0f;
	                        
	                        vTexture[1].x = ((vVertex[1].x * fRadialScale) + 1.0f) * 0.5f;
	                        vTexture[1].y = ((vVertex[1].y * fRadialScale) + 1.0f) * 0.5f;
	                        
	                        // Inner Second
	                        vVertex[2] = new Vector3f();
	                        vNormal[2] = new Vector3f();
	                        vTexture[2] = new Vector2f();
	                        vVertex[2].x = (float)Math.cos(theytaNext) * inner;        // X    
	                        vVertex[2].y = (float)Math.sin(theytaNext) * inner;        // Y
	                        vVertex[2].z = 0.0f;                                   // Z
	                        
	                        vNormal[2].x = 0.0f;                                   // Surface Normal, same for everybody
	                        vNormal[2].y = 0.0f;
	                        vNormal[2].z = 1.0f;
	                        
	                        vTexture[2].x = ((vVertex[2].x * fRadialScale) + 1.0f) * 0.5f;
	                        vTexture[2].y = ((vVertex[2].y * fRadialScale) + 1.0f) * 0.5f;
	                        
	                        
	                        // Outer Second
	                        vVertex[3] = new Vector3f();
	                        vNormal[3] = new Vector3f();
	                        vTexture[3] = new Vector2f();
	                        vVertex[3].x = (float)Math.cos(theytaNext) * outer;        // X    
	                        vVertex[3].y = (float)Math.sin(theytaNext) * outer;        // Y
	                        vVertex[3].z = 0.0f;                                   // Z
	                        
	                        vNormal[3].x = 0.0f;                                   // Surface Normal, same for everybody
	                        vNormal[3].y = 0.0f;
	                        vNormal[3].z = 1.0f;
	                        
	                        vTexture[3].x = ((vVertex[3].x * fRadialScale) + 1.0f) * 0.5f;
	                        vTexture[3].y = ((vVertex[3].y * fRadialScale) + 1.0f) * 0.5f;
	                        
	                        drawTriangle(vVertex, vNormal, vTexture);                      
	                        
	                        // Rearrange for next triangle
	                        vVertex[0].set(vVertex[1]);
	                        vNormal[0].set(vNormal[1]);
	                        vTexture[0].set(vTexture[1]);
	                        
	                        vVertex[1].set(vVertex[3]);
	                        vNormal[1].set(vNormal[3]);
	                        vTexture[1].set(vTexture[3]);
	                                        
	                        drawTriangle(vVertex, vNormal, vTexture);                      
	                        }
	                }
	        
	        GL11.glEnd();
	        }
	
	private static void drawTriangle(Vector3f[] vVertex, Vector3f[] vNormal,
			Vector2f[] vTexture) {
		GL11.glVertex3f(vVertex[0].x, vVertex[0].y, vVertex[0].z);
		GL11.glVertex3f(vVertex[1].x, vVertex[1].y, vVertex[1].z);
		GL11.glVertex3f(vVertex[2].x, vVertex[2].y, vVertex[2].z);
	}

	public static FloatBuffer directFloatBuffer(float[]buffer){
		ByteBuffer bb = ByteBuffer.allocateDirect(4*buffer.length);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(buffer).flip();
		return fb;
	}

	public static IntBuffer directIntBuffer(int[]buffer){
		ByteBuffer bb = ByteBuffer.allocateDirect(4*buffer.length);
		bb.order(ByteOrder.nativeOrder());
		IntBuffer ib = bb.asIntBuffer();
		ib.put(buffer).flip();
		return ib;
	}


}

