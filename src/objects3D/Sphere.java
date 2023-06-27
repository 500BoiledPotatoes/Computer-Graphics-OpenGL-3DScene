package objects3D;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Sphere {

	public Sphere() {

	}

	// Implement using notes and examine Tetrahedron to aid in the coding look at
	// lecture 7 , 7b and 8
	// 7b should be your primary source, we will cover more about circles in later
	// lectures to understand why the code works
	public void drawSphere(float radius, float nSlices, float nSegments) {
		float inctheta = (float) ((2.0f * Math.PI) / nSlices);
		float incphi = (float) (Math.PI / nSegments);
		//Calculate the Angle of change
		GL11.glBegin(GL11.GL_QUADS);
		for (float theta = (float) -Math.PI; theta < Math.PI; theta += inctheta) {
			for (float phi = (float) -(Math.PI / 2.0f); phi < (Math.PI / 2.0f); phi += incphi) {
				float r = (float) (radius * Math.cos(phi));
				float z = (float) (radius * Math.sin(phi));
				//Calculate the coordinates of z
				float x = (float) (radius * Math.cos(phi) * Math.cos(theta));
				//Calculate the coordinates of x
				float y = (float) (radius * Math.cos(phi) * Math.sin(theta));
				//Calculate the coordinates of x

				//Find the four points on the ball and first draw a rectangle
				GL11.glNormal3f(x, y, z);
				GL11.glVertex3f(x, y, z);
				//The first point

				GL11.glNormal3f((float) (r * Math.cos(theta+inctheta)), (float) (r * Math.sin(theta+inctheta)), z);
				GL11.glVertex3f((float) (r * Math.cos(theta+inctheta)), (float) (r* Math.sin(theta+inctheta)), z);
				//The second point
				GL11.glNormal3f((float) (radius * Math.cos(phi+incphi) * Math.cos(theta+inctheta)), (float) (radius * Math.cos(phi+incphi) * Math.sin(theta+inctheta)), (float) (radius * Math.sin(phi+incphi)));
				GL11.glVertex3f((float) (radius* Math.cos(phi+incphi) * Math.cos(theta+inctheta)), (float) (radius * Math.cos(phi+incphi) * Math.sin(theta+inctheta)), (float) (radius * Math.sin(phi+incphi)));
				//The third point
				GL11.glNormal3f((float) (radius * Math.cos(phi+incphi) * Math.cos(theta)), (float) (radius * Math.cos(phi+incphi) * Math.sin(theta)), (float) (radius * Math.sin(phi+incphi)));
				GL11.glVertex3f((float) (radius * Math.cos(phi+incphi) * Math.cos(theta)), (float) (radius * Math.cos(phi+incphi) * Math.sin(theta)), (float) (radius* Math.sin(phi+incphi)));
				//The fourth point
			}
		}
		GL11.glEnd();
	}
}
