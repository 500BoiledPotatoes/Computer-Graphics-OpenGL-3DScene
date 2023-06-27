
import java.io.IOException;
import java.nio.FloatBuffer;

import objects3D.TexCube;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import GraphicsObjects.Arcball;
import GraphicsObjects.Utils;
import objects3D.TexSphere;
import objects3D.Grid;
import objects3D.Human;

//Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment. 
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment. 
// 

// Do not touch this class, I will be making a version of it for your 3rd Assignment 
public class MainWindow {

	private boolean MouseOnepressed = true;
	private boolean dragMode = false;
	private boolean BadAnimation = true;
	private boolean Earth = false;
	/** position of pointer */
	float x = 600, y = 400;
	/** angle of rotation */
	float rotation = 0;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	long myDelta = 0; // to use for animation
	float Alpha = 0; // to use for animation
	long StartTime; // beginAnimiation

	Arcball MyArcball = new Arcball();

	boolean DRAWGRID = false;
	boolean waitForKeyrelease = true;
	/** Mouse movement */
	int LastMouseX = -1;
	int LastMouseY = -1;

	float pullX = 0.0f; // arc ball X cord.
	float pullY = 0.0f; // arc ball Y cord.

	int OrthoNumber = 1200; // using this for screen size, making a window of 1200 x 800 so aspect ratio 3:2
							// // do not change this for assignment 3 but you can change everything for your
							// project

	// basic colours
	static float black[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	static float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	static float grey[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	static float spot[] = { 0.1f, 0.1f, 0.1f, 0.5f };

	// primary colours
	static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	static float green[] = { 0.0f, 1.0f, 0.0f, 1.0f };
	static float blue[] = { 0.0f, 0.0f, 1.0f, 1.0f };

	// secondary colours
	static float yellow[] = { 1.0f, 1.0f, 0.0f, 1.0f };
	static float magenta[] = { 1.0f, 0.0f, 1.0f, 1.0f };
	static float cyan[] = { 0.0f, 1.0f, 1.0f, 1.0f };

	// other colours
	static float orange[] = { 1.0f, 0.5f, 0.0f, 1.0f, 1.0f };
	static float brown[] = { 0.5f, 0.25f, 0.0f, 1.0f, 1.0f };
	static float dkgreen[] = { 0.0f, 0.5f, 0.0f, 1.0f, 1.0f };
	static float pink[] = { 1.0f, 0.6f, 0.6f, 1.0f, 1.0f };

	// static GLfloat light_position[] = {0.0, 100.0, 100.0, 0.0};

	// support method to aid in converting a java float array into a Floatbuffer
	// which is faster for the opengl layer to process

	public void start() {

		StartTime = getTime();
		try {
			Display.setDisplayMode(new DisplayMode(1200, 800));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			update(delta);
			renderGL();
			Display.update();
			Display.sync(120); // cap fps to 120fps
		}

		Display.destroy();
	}

	public void update(int delta) {
		// rotate quad
		// rotation += 0.01f * delta;
		int MouseX = Mouse.getX();
		int MouseY = Mouse.getY();
		int WheelPostion = Mouse.getDWheel();

		boolean MouseButonPressed = Mouse.isButtonDown(0);

		if (MouseButonPressed && !MouseOnepressed) {
			MouseOnepressed = true;
			// System.out.println("Mouse drag mode");
			MyArcball.startBall(MouseX, MouseY, 1200, 800);
			dragMode = true;

		} else if (!MouseButonPressed) {
			// System.out.println("Mouse drag mode end ");
			MouseOnepressed = false;
			dragMode = false;
		}

		if (dragMode) {
			MyArcball.updateBall(MouseX, MouseY, 1200, 800);
		}

		if (WheelPostion > 0) {
			OrthoNumber += 10;
		}

		if (WheelPostion < 0) {
			OrthoNumber -= 10;
			if (OrthoNumber < 610) {
				OrthoNumber = 610;
			}
			// System.out.println("Orth nubmer = " + OrthoNumber);
		}

		/** rest key is R */
		if (Keyboard.isKeyDown(Keyboard.KEY_R))
			MyArcball.reset();

		/* bad animation can be turn on or off using A key) */

		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			BadAnimation = !BadAnimation;
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			x += 0.35f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			y += 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			y -= 0.35f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_Q))
			rotation += 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			Earth = !Earth;
		}
		
		if (waitForKeyrelease) // check done to see if key is released
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_G)) {

				DRAWGRID = !DRAWGRID;
				Keyboard.next();
				if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
					waitForKeyrelease = true;
				} else {
					waitForKeyrelease = false;

				}
			}
		}

		/** to check if key is released */
		if (Keyboard.isKeyDown(Keyboard.KEY_G) == false) {
			waitForKeyrelease = true;
		} else {
			waitForKeyrelease = false;

		}

		// keep quad on the screen
		if (x < 0)
			x = 0;
		if (x > 1200)
			x = 1200;
		if (y < 0)
			y = 0;
		if (y > 800)
			y = 800;

		updateFPS(); // update FPS Counter

		LastMouseX = MouseX;
		LastMouseY = MouseY;
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		changeOrth();
		MyArcball.startBall(0, 0, 1200, 800);
		glMatrixMode(GL_MODELVIEW);
		FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
		lightPos.put(10000f).put(1000f).put(1000).put(0).flip();

		FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
		lightPos2.put(0f).put(1000f).put(0).put(-1000f).flip();

		FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
		lightPos3.put(-10000f).put(1000f).put(1000).put(0).flip();

		FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
		lightPos4.put(1000f).put(1000f).put(1000f).put(0).flip();

		glLight(GL_LIGHT0, GL_POSITION, lightPos); // specify the
													// position
													// of the
													// light
		// glEnable(GL_LIGHT0); // switch light #0 on // I've setup specific materials
		// so in real light it will look abit strange

		glLight(GL_LIGHT1, GL_POSITION, lightPos); // specify the
													// position
													// of the
													// light
		glEnable(GL_LIGHT1); // switch light #0 on
		glLight(GL_LIGHT1, GL_DIFFUSE, Utils.ConvertForGL(spot));

		glLight(GL_LIGHT2, GL_POSITION, lightPos3); // specify
													// the
													// position
													// of the
													// light
		glEnable(GL_LIGHT2); // switch light #0 on
		glLight(GL_LIGHT2, GL_DIFFUSE, Utils.ConvertForGL(grey));

		glLight(GL_LIGHT3, GL_POSITION, lightPos4); // specify
													// the
													// position
													// of the
													// light
		glEnable(GL_LIGHT3); // switch light #0 on
		glLight(GL_LIGHT3, GL_DIFFUSE, Utils.ConvertForGL(grey));

		glEnable(GL_LIGHTING); // switch lighting on
		glEnable(GL_DEPTH_TEST); // make sure depth buffer is switched
									// on
		glEnable(GL_NORMALIZE); // normalize normal vectors for safety
		glEnable(GL_COLOR_MATERIAL);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		try {
			init();
			//Initializing Materials
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // load in texture

	}

	public void changeOrth() {

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(1200 - OrthoNumber, OrthoNumber, (800 - (OrthoNumber * 0.66f)), (OrthoNumber * 0.66f), 100000, -100000);
		glMatrixMode(GL_MODELVIEW);

		FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16);
		glGetFloat(GL_MODELVIEW_MATRIX, CurrentMatrix);

		// if(MouseOnepressed)
		// {

		MyArcball.getMatrix(CurrentMatrix);
		// }

		glLoadMatrix(CurrentMatrix);

	}

	/*
	 * You can edit this method to add in your own objects / remember to load in
	 * textures in the INIT method as they take time to load
	 * 
	 */
	public void renderGL() {


		changeOrth();

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glColor3f(0.5f, 0.5f, 1.0f);

		myDelta = getTime() - StartTime;
		float delta = ((float) myDelta) / 10000;
		// code to aid in animation
		float theta = (float) (delta * 2 * Math.PI);
		float thetaDeg = delta * 360;
		float posn_x = (float) Math.cos(theta); // same as your circle code in your notes
		float posn_y = (float) Math.sin(theta);

		/*
		 * This code draws a grid to help you view the human models movement You may
		 * change this code to move the grid around and change its starting angle as you
		 * please
		 */
		if (DRAWGRID) {
			glPushMatrix();
			Grid MyGrid = new Grid();
			glTranslatef(600, 400, 0);
			glScalef(200f, 200f, 200f);
			MyGrid.DrawGrid();
			glPopMatrix();
		}

		glPushMatrix();
		Human MyHuman = new Human();
		glTranslatef(300, 400, 0);
		glScalef(90f, 90f, 90f);

		if (!BadAnimation) {
			// insert your animation code to correct the postion for the human rotating
			glTranslatef(-posn_x * 3.0f, 0.0f, -posn_y * 3.0f);
			glRotatef(-thetaDeg,0,1,0);
			//Let the person rotate the direction with the turn
		} else {
			// bad animation version
			glTranslatef(-posn_x * 3.0f, 0.0f, -posn_y * 3.0f);
		}

		MyHuman.drawHuman(delta, !BadAnimation); // give a delta for the Human object ot be animated

		glPopMatrix();

		/*
		 * This code puts the earth code in which is larger than the human so it appears
		 * to change the scene
		 */

		//Use a cube to make a door
		glPopMatrix();
		glPushMatrix();
		TexCube MyCube = new TexCube();
		glTranslatef(500, 270, 500);
		glScalef(10000f, 0.0f, 10000f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_ground.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube.drawTexCube(texture_ground);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube1 = new TexCube();
		glTranslatef(330, 350, 500);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube1.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube2 = new TexCube();
		glTranslatef(330, 425, 500);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube2.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube3 = new TexCube();
		glTranslatef(330, 500, 500);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube3.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube4 = new TexCube();
		glTranslatef(330, 575, 500);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube4.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube5 = new TexCube();
		glTranslatef(330, 720, 500);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube5.drawTexCube(texture_door);
		glPopMatrix();


		glPopMatrix();
		glPushMatrix();
		TexCube MyCube6 = new TexCube();
		glTranslatef(330, 720, 350);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube6.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube7 = new TexCube();
		glTranslatef(330, 720, 200);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube7.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube8 = new TexCube();
		glTranslatef(330, 720, 50);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube8.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube9 = new TexCube();
		glTranslatef(330, 600, 50);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube9.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube10 = new TexCube();
		glTranslatef(330, 450, 50);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube10.drawTexCube(texture_door);
		glPopMatrix();


		glPopMatrix();
		glPushMatrix();
		TexCube MyCube11 = new TexCube();
		glTranslatef(330, 600, 50);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube11.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube12 = new TexCube();
		glTranslatef(330, 350, 50);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube12.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube13 = new TexCube();
		glTranslatef(330, 350, 50);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube13.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube14 = new TexCube();
		glTranslatef(330, 720, -100);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube14.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube15 = new TexCube();
		glTranslatef(330, 720, -250);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube15.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube16 = new TexCube();
		glTranslatef(330, 720, -480);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube16.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube17 = new TexCube();
		glTranslatef(330, 600, -480);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube17.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube18= new TexCube();
		glTranslatef(330, 450, -480);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube18.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube19 = new TexCube();
		glTranslatef(330, 350, -480);
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube19.drawTexCube(texture_door);
		glPopMatrix();

		glPopMatrix();
		glPushMatrix();
		TexCube MyCube20 = new TexCube();
		glTranslatef(330, 720, -350 );
		glScalef(75f, 75f, 75f);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		Color.white.bind();
		texture_door.bind();
		glEnable(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MyCube20.drawTexCube(texture_door);
		glPopMatrix();


		if (Earth) {
			// Globe in the centre of the scene
			glPushMatrix();
			TexSphere MyGlobe = new TexSphere();
			// TexCube MyGlobe = new TexCube();
			glTranslatef(500, 500, 500);
			glScalef(140f, 140f, 140f);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
			Color.white.bind();
			texture.bind();
			glEnable(GL_TEXTURE_2D);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			MyGlobe.DrawTexSphere(8f, 100, 100, texture);
			// MyGlobe.DrawTexCube();
			glPopMatrix();
		}

	}

	public static void main(String[] argv) {
		MainWindow hello = new MainWindow();
		hello.start();
	}

	Texture texture;
	Texture texture_ground;
	Texture texture_door;

	/*
	 * Any additional textures for your assignment should be written in here. Make a
	 * new texture variable for each one so they can be loaded in at the beginning
	 */
	public void init() throws IOException {
		texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/wood.png"));
		texture_ground = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ground.png"));
		texture_door = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/door.png"));
		System.out.println("Texture loaded okay ");
	}
}
