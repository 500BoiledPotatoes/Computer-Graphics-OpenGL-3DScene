package objects3D;

import static org.lwjgl.opengl.GL11.*;
import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

import java.math.*;

public class Cylinder {

	
	public Cylinder() { 
	}
	
	// remember to use Math.PI isntead PI 
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	public void drawCylinder(float radius, float height, int nSegments ) 
	{
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (float i = 0.0F; i < nSegments; i += 1.0) { /* a loop around circumference of a tube */
			float angle = (float) (Math.PI * i * 2.0 / nSegments);
			float nextAngle = (float) (Math.PI * (i + 1.0) * 2.0 / nSegments);
			/* compute sin & cosine */
			float x1 = (float) Math.sin(angle), y1 = (float) Math.cos(angle);
			float x2 = (float) Math.sin(nextAngle), y2 = (float) Math.cos(nextAngle);
			/* draw top (green) triangle */
			//Find the three points of the triangle
			GL11.glNormal3f(radius * x1, radius * y1, 0.0f);
			GL11.glVertex3f(radius * x1, radius * y1, 0.0f);
			GL11.glNormal3f(radius * x2, radius * y2, 0.0f);
			GL11.glVertex3f(radius * x2, radius * y2, height);
			GL11.glNormal3f(radius * x1, radius * y1, 0.0f);
			GL11.glVertex3f(radius * x1, radius * y1, height);
			/* draw bottom (red) triangle */
			//Find the three points of the triangle
			GL11.glNormal3f(radius * x1, radius * y1, 0.0f);
			GL11.glVertex3f(radius * x1, radius * y1, 0.0f);
			GL11.glNormal3f(radius * x2, radius * y2, 0.0f);
			GL11.glVertex3f(radius * x2, radius * y2, 0.0f);
			GL11.glNormal3f(radius * x2, radius * y2, 0.0f);
			GL11.glVertex3f(radius * x2, radius * y2, height);
			//Draw the top surface of the cylinder
			//By moving the points below the triangle to the center of the top circle
			GL11.glNormal3f(radius * x1, radius * y1, 0.0f);
			GL11.glVertex3f(radius * x1, radius * y1, 0.0f);
			GL11.glNormal3f(radius * x2, radius * y2, 0.0f);
			GL11.glVertex3f(radius * x2, radius * y2, 0.0f);
			GL11.glNormal3f(radius * x1, radius * y1, 0.0f);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);
			//Draw the base of the cylinder
			//By moving the points from the top of the triangle to the center of the bottom
			GL11.glNormal3f(radius * x1, radius * y1, 0.0f);
			GL11.glVertex3f(radius * x1, radius * y1, height);
			GL11.glNormal3f(radius * x2, radius * y2, 0.0f);
			GL11.glVertex3f(radius * x2, radius * y2, height);
			GL11.glNormal3f(radius * x2, radius * y2, 0.0f);
			GL11.glVertex3f(0.0f, 0.0f, height);
		}
		GL11.glEnd();
	}
}
