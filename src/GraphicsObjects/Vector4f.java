package GraphicsObjects;

public class Vector4f {

	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float a = 0;

	public Vector4f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		a = 0.0f;
	}

	public Vector4f(float x, float y, float z, float a) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}

	// implement Vector plus a Vector
	public Vector4f PlusVector(Vector4f Additonal) {
		return new Vector4f(this.x + Additonal.x, this.y + Additonal.y, this.z + Additonal.z, this.a + Additonal.a);
	}

	// implement Vector minus a Vector
	public Vector4f MinusVector(Vector4f Minus) {
		return new Vector4f(x - Minus.x, y - Minus.y, z - Minus.z, a - Minus.a);
		//The x, y, z coordinates of each vector minus the x, y, z in the input vector
		//The result is a vector
	}

	// implement Vector plus a Point
	public Point4f PlusPoint(Point4f Additonal) {
		return new Point4f(x + Additonal.x, y + Additonal.y, z + Additonal.z, a + Additonal.a);
		//The x, y, z coordinates of each vector plus the x, y, z in the input point
		//The result is a point
	}
	// Do not implement Vector minus a Point as it is undefined

	// Implement a Vector * Scalar
	public Vector4f byScalar(float scale) {
		return new Vector4f(x * scale, y * scale, z * scale, a * scale);
		//Each coordinate of the vector is multiplied by the input scalar
		//The result is a vector
	}

	// implement returning the negative of a Vector
	public Vector4f NegateVector() {
		return new Vector4f(-x, -y, -z, -a);
		//Each coordinate of the vector becomes a negative
	}

	// implement getting the length of a Vector
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + a * a);
	}

	// Just to avoid confusion here is getting the Normal of a Vector
	public Vector4f Normal() {
		float LengthOfTheVector = this.length();
		return this.byScalar(1.0f / LengthOfTheVector);
	}

	// implement getting the dot product of Vector.Vector

	public float dot(Vector4f v) {
		return ( this.x*v.x + this.y*v.y + this.z*v.z+ this.a*v.a);
	}

	// Implemented this for you to avoid confusion
	// as we will not normally be using 4 float vector
	public Vector4f cross(Vector4f v) {
		float u0 = (this.y * v.z - z * v.y);
		float u1 = (z * v.x - x * v.z);
		float u2 = (x * v.y - y * v.x);
		float u3 = 0; // ignoring this for now
		return new Vector4f(u0, u1, u2, u3);
	}

}
	 
	   

/*

										MMMM                                        
										MMMMMM                                      
 										MM MMMM                                    
 										MMI  MMMM                                  
 										MMM    MMMM                                
 										MMM      MMMM                              
  										MM        MMMMM                           
  										MMM         MMMMM                         
  										MMM           OMMMM                       
   										MM             .MMMM                     
MMMMMMMMMMMMMMM                        MMM              .MMMM                   
MM   IMMMMMMMMMMMMMMMMMMMMMMMM         MMM                 MMMM                 
MM                  ~MMMMMMMMMMMMMMMMMMMMM                   MMMM               
MM                                  OMMMMM                     MMMMM            
MM                                                               MMMMM          
MM                                                                 MMMMM        
MM                                                                   ~MMMM      
MM                                                                     =MMMM    
MM                                                                        MMMM  
MM                                                                       MMMMMM 
MM                                                                     MMMMMMMM 
MM                                                                  :MMMMMMMM   
MM                                                                MMMMMMMMM     
MM                                                              MMMMMMMMM       
MM                             ,MMMMMMMMMM                    MMMMMMMMM         
MM              IMMMMMMMMMMMMMMMMMMMMMMMMM                  MMMMMMMM            
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM               ZMMMMMMMM              
MMMMMMMMMMMMMMMMMMMMMMMMMMMMM          MM$             MMMMMMMMM                
MMMMMMMMMMMMMM                       MMM            MMMMMMMMM                  
  									MMM          MMMMMMMM                     
  									MM~       IMMMMMMMM                       
  									MM      DMMMMMMMM                         
 								MMM    MMMMMMMMM                           
 								MMD  MMMMMMMM                              
								MMM MMMMMMMM                                
								MMMMMMMMMM                                  
								MMMMMMMM                                    
  								MMMM                                      
  								MM                                        
                             GlassGiant.com */