package objects3D;

import static org.lwjgl.opengl.GL11.*;

import GraphicsObjects.Point4f;
import GraphicsObjects.Utils;
import GraphicsObjects.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Human {

    // basic colours
    static float black[] = {0.0f, 0.0f, 0.0f, 1.0f};
    static float white[] = {1.0f, 1.0f, 1.0f, 1.0f};

    static float grey[] = {0.5f, 0.5f, 0.5f, 1.0f};
    static float spot[] = {0.1f, 0.1f, 0.1f, 0.5f};

    // primary colours
    static float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
    static float green[] = {0.0f, 1.0f, 0.0f, 1.0f};
    static float blue[] = {0.0f, 0.0f, 1.0f, 1.0f};

    // secondary colours
    static float yellow[] = {1.0f, 1.0f, 0.0f, 1.0f};
    static float magenta[] = {1.0f, 0.0f, 1.0f, 1.0f};
    static float cyan[] = {0.0f, 1.0f, 1.0f, 1.0f};

    // other colours
    static float orange[] = {1.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float brown[] = {0.5f, 0.25f, 0.0f, 1.0f, 1.0f};
    static float dkgreen[] = {0.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float pink[] = {1.0f, 0.6f, 0.6f, 1.0f, 1.0f};

    static Texture texture;
    static Texture texture_body;

    static Texture texture_body1;

    static Texture texture_pelvis;

    static {
        try {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/head.png"));
            texture_body = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/body.png"));
            texture_body1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/body1.png"));
            texture_pelvis = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/pelvis.png"));
            //Import material pictures
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Human() {

    }
    // Implement using notes in Animation lecture
    public void drawHuman(float delta, boolean GoodAnimation) {
        float theta = (float) (delta * 15 * Math.PI);
        //The speed at which a person moves
        float LimbRotation;
        if (GoodAnimation) {
            LimbRotation = (float) Math.cos(theta) * 60;
        } else {
            LimbRotation = 0;
        }
        Cylinder cylinder = new Cylinder();
        TexSphere Mysphere = new TexSphere();

        glPushMatrix();
        //Push current matrix
        {
            // pelvis
            glTranslatef(0.0f, 0.5f, 0.0f);
            //It moves along the x,y, and z axes
            Color.white.bind();
            //Bind background color white
            texture_pelvis.bind();
            //Binding material picture
            glEnable(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            //Specifies the clearing value of the color buffer
            Mysphere.DrawTexSphere(0.5f, 32, 32, texture_pelvis);
            // chest
            glPushMatrix();
            {
                glTranslatef(0.0f, 0.5f, 0.0f);
                glRotatef((float) (LimbRotation/ 1.5), 0.0f, 5.0f, 0.0f);
                //Transverse rotation of chest
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                Color.white.bind();
                texture_body.bind();
                glEnable(GL_TEXTURE_2D);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                Mysphere.DrawTexSphere(0.5f, 32, 32, texture_body);
                //Set the chest material

                // neck
                glColor3f(orange[0], orange[1], orange[2]);
                //Set neck color
                glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                //Specify the material parameters of the lighting model
                glPushMatrix();
                {
                    glTranslatef(0.0f, 0.0f, 0.0f);
                    glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                    //Adjust neck position
					glRotatef((LimbRotation / 8), 0.0f, 5.0f, 0.0f);
                    //Set the direction and magnitude of neck rotation
                    cylinder.drawCylinder(0.15f, 0.7f, 32);
                    // head
                    glPushMatrix();
                    {
                        glTranslatef(0.0f, 0.0f, 1.0f);
                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                        Color.white.bind();
                        texture.bind();
                        glEnable(GL_TEXTURE_2D);
                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                        Mysphere.DrawTexSphere(0.5f, 32, 32, texture);
                        glPopMatrix();
                        //Set the head material
                    }
                    glPopMatrix();
                    //Get rid of copy

                    // left shoulder
                    glPushMatrix();
                    {
                        glTranslatef(0.5f, 0.4f, 0.0f);
                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                        Color.white.bind();
                        texture_body1.bind();
                        glEnable(GL_TEXTURE_2D);
                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                        Mysphere.DrawTexSphere(0.25f, 32, 32, texture_body1);
                        //Set the left shoulder material

                        // left arm
                        glColor3f(orange[0], orange[1], orange[2]);
                        glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                        glPushMatrix();
                        {
                            glTranslatef(0.0f, 0.0f, 0.0f);
                            glRotatef(90.0f, 1.0f, 0.5f, 0.0f);
                            glRotatef(-LimbRotation, -1.0f, 0.0f, 0.0f);
                            cylinder.drawCylinder(0.15f, 0.7f, 32);
                            //Set the position and rotation amplitude of the left arm

                            // left elbow
                            glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                            glPushMatrix();
                            {
                                glTranslatef(0.0f, 0.0f, 0.75f);
                                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                                Color.white.bind();
                                texture_body1.bind();
                                glEnable(GL_TEXTURE_2D);
                                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                                glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                                Mysphere.DrawTexSphere(0.2f, 32, 32, texture_body1);
                                //Set the left elbow material

                                // left forearm
                                glColor3f(orange[0], orange[1], orange[2]);
                                glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                                glPushMatrix();
                                {
                                    glTranslatef(0.0f, 0.0f, 0.0f);
                                    glRotatef(90.0f, 10.0f, -0.3f, 0.0f);
                                    cylinder.drawCylinder(0.1f, 0.7f, 32);
                                    //Set the position and rotation amplitude of the left forearm

                                    // left hand
                                    glPushMatrix();
                                    {
                                        glTranslatef(0.0f, 0.0f, 0.75f);
                                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                                        Color.white.bind();
                                        texture_body1.bind();
                                        glEnable(GL_TEXTURE_2D);
                                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                                        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                                        Mysphere.DrawTexSphere(0.2f, 32, 32, texture_body1);
                                        //Set the left hand material
                                    }
                                    glPopMatrix();
                                }
                                glPopMatrix();
                            }
                            glPopMatrix();
                        }
                        glPopMatrix();
                    }
                    glPopMatrix();
                    // to chest

                    // right shoulder
					glPushMatrix();
					{
						glTranslatef(-0.5f, 0.4f, 0.0f);
                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                        Color.white.bind();
                        texture_body1.bind();
                        glEnable(GL_TEXTURE_2D);
                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                        Mysphere.DrawTexSphere(0.25f, 32, 32, texture_body1);
                        //Set the right shoulder material

						// right arm
                        glColor3f(orange[0], orange[1], orange[2]);
						glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
						glPushMatrix();
						{
							glTranslatef(0.0f, 0.0f, 0.0f);
							glRotatef(90.0f, 1.0f, -0.5f, 0.0f);
                            glRotatef(-LimbRotation, 1.0f, 0.0f, 0.0f);
							cylinder.drawCylinder(0.15f, 0.7f, 32);

							// right elbow
							glPushMatrix();
							{
								glTranslatef(0.0f, 0.0f, 0.75f);
                                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                                Color.white.bind();
                                texture_body1.bind();
                                glEnable(GL_TEXTURE_2D);
                                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                                glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                                Mysphere.DrawTexSphere(0.2f, 32, 32, texture_body1);
                                //Set the right elbow material

								// right forearm
								glColor3f(orange[0], orange[1], orange[2]);
								glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
								glPushMatrix();
								{
									glTranslatef(0.0f, 0.0f, 0.0f);
									glRotatef(90.0f, 10.0f, 0.3f, 0.0f);
									cylinder.drawCylinder(0.1f, 0.7f, 32);
                                    //Set the position and rotation amplitude of the right forearm

									// right hand
									glPushMatrix();
									{
										glTranslatef(0.0f, 0.0f, 0.75f);
                                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                                        Color.white.bind();
                                        texture_body1.bind();
                                        glEnable(GL_TEXTURE_2D);
                                        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                                        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                                        Mysphere.DrawTexSphere(0.2f, 32, 32, texture_body1);
                                        //Set the right hand material
                                    }
									glPopMatrix();
								}
								glPopMatrix();
							}
							glPopMatrix();
						}
						glPopMatrix();
					}
					glPopMatrix();

                }
                glPopMatrix();
                // left hip
                glPushMatrix();
                {
                    glTranslatef(-0.5f, -0.2f, 0.0f);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                    Color.white.bind();
                    texture_body1.bind();
                    glEnable(GL_TEXTURE_2D);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    Mysphere.DrawTexSphere(0.25f, 32, 32, texture_body1);
                    //Set the left hip material

                    // left high leg
                    glColor3f(orange[0], orange[1], orange[2]);
                    glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                    glPushMatrix();
                    {
                        glTranslatef(0.0f, 0.0f, 0.0f);
                        glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
                        glRotatef((LimbRotation / 2) + 90, 1.0f, 0.0f, 0.0f);
                        cylinder.drawCylinder(0.15f, 0.7f, 32);
                        //Set the position and rotation amplitude of the left high leg

                        // left knee
                        glPushMatrix();
                        {
                            glTranslatef(0.0f, 0.0f, 0.75f);
                            glRotatef(5.0f, 0.0f, 0.0f, 0.0f);
                            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                            Color.white.bind();
                            texture_body1.bind();
                            glEnable(GL_TEXTURE_2D);
                            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                            Mysphere.DrawTexSphere(0.25f, 32, 32, texture_body1);
                            //Set the left knee material

                            // left low leg
                            glColor3f(orange[0], orange[1], orange[2]);
                            glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                            glPushMatrix();
                            {
                                glTranslatef(0.0f, 0.0f, 0.0f);
                                cylinder.drawCylinder(0.15f, 0.7f, 32);
                                //Set the position and rotation amplitude of the left low leg

                                // left foot
                                glColor3f(blue[0], blue[1], blue[2]);
                                glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                                glPushMatrix();
                                {
                                    glTranslatef(0.0f, 0.0f, 0.75f);
                                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                                    Color.white.bind();
                                    texture_body1.bind();
                                    glEnable(GL_TEXTURE_2D);
                                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                                    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                                    Mysphere.DrawTexSphere(0.3f, 32, 32, texture_body1);
                                    //Set the left foot material
                                }
                                glPopMatrix();
                            }
                            glPopMatrix();
                        }
                        glPopMatrix();
                    }
                    glPopMatrix();
                }
                glPopMatrix();

                // right hip
				glPushMatrix();
				{
					glTranslatef(0.5f, -0.2f, 0.0f);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                    Color.white.bind();
                    texture_body1.bind();
                    glEnable(GL_TEXTURE_2D);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    Mysphere.DrawTexSphere(0.25f, 32, 32, texture_body1);
                    //Set the right hip material

					// right high leg
					glColor3f(orange[0], orange[1], orange[2]);
					glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
					glPushMatrix();
					{
						glTranslatef(0.0f, 0.0f, 0.0f);
						glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
                        glRotatef((-LimbRotation / 2) + 90, 1.0f, 0.0f, 0.0f);
						cylinder.drawCylinder(0.15f, 0.7f, 32);
                        //Set the position and rotation amplitude of the right high leg

						// right knee
						glPushMatrix();
						{
							glTranslatef(0.0f, 0.0f, 0.75f);
							glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
                            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                            Color.white.bind();
                            texture_body1.bind();
                            glEnable(GL_TEXTURE_2D);
                            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                            Mysphere.DrawTexSphere(0.25f, 32, 32, texture_body1);
                            //Set the right knee material

							// right low leg
							glColor3f(orange[0], orange[1], orange[2]);
							glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
							glPushMatrix();
							{
								glTranslatef(0.0f, 0.0f, 0.0f);
								cylinder.drawCylinder(0.15f, 0.7f, 32);
                                //Set the position and rotation amplitude of the right low leg

								// right foot
								glPushMatrix();
								{
									glTranslatef(0.0f, 0.0f, 0.75f);
                                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                                    Color.white.bind();
                                    texture_body1.bind();
                                    glEnable(GL_TEXTURE_2D);
                                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                                    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                                    Mysphere.DrawTexSphere(0.3f, 32, 32, texture_body1);
                                    //Set the right foot material
                                }
								glPopMatrix();
							}
							glPopMatrix();
						}
						glPopMatrix();
					}
					glPopMatrix();
				}
				glPopMatrix();
            }
            glPopMatrix();
        }
    }
}

/*
 *
 *
 * }
 *
 */
