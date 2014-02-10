package com.example.thelonely;

public class Collision {
	
	
	public static boolean creatureProductCollision(Creature cre, Product pro){
		if( (Math.pow((cre.getxPos() - pro.getX()), 2) 
				+ Math.pow((cre.getyPos() - pro.getY()),2))
				< Math.pow((cre.getR() + pro.getR()),2) ){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean productCrateCollision(Product pro, Crate cre){
		if( (Math.pow((cre.getX() - pro.getX()), 2) 
				+ Math.pow((cre.getY() - pro.getY()),2))
				< Math.pow((cre.getR() + pro.getR()),2) ){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean creaturePlatformCollision(Creature cre, Platform plat){
		if(((cre.getyPos() + cre.getR()) > plat.y) && (cre.getyPos() < plat.y)){
			if((cre.getxPos() > plat.x) && ((cre.getxPos() + cre.getR()) < (plat.x + plat.length))){
				return true;				
			}			
		}
		
		return false;
	}
	
	public static boolean creatureEggCollision(Creature cre, Egg egg){
		
		
		//if egg is hatching, dont count as colliison
		if(egg.isHatching)return false;
		
		if( (Math.pow((cre.getxPos() - egg.getX()), 2) 
				+ Math.pow((cre.getyPos() - egg.getY()),2))
				< Math.pow((cre.getR() + egg.getR()),2) ){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean eggCrateCollision(Egg egg, Crate cre){
		if( (Math.pow((cre.getX() - egg.getX()), 2) 
				+ Math.pow((cre.getY() - egg.getY()),2))
				< Math.pow((cre.getR() + egg.getR()),2) ){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean eggProductCollision(Egg egg, Product pro){
		if( (Math.pow((pro.getX() - egg.getX()), 2) 
				+ Math.pow((pro.getY() - egg.getY()),2))
				< Math.pow((pro.getR() + egg.getR()),2) ){
			return true;
		}else{
			return false;
		}
	}
	
	//POISON
//	public static boolean productFireCollision(Product pro, Fire fir){
//		if( (Math.pow((pro.getX() - fir.getX()), 2) 
//				+ Math.pow((pro.getY() - fir.getY()),2))
//				< Math.pow((pro.getR() + fir.getR()),2) ){
//			return true;
//		}else{
//			return false;
//		}
//	}
	
	public static boolean flyCreProductCollision(FlyCre fly, Product pro){
		if( (Math.pow((pro.getX() - fly.x), 2) 
				+ Math.pow((pro.getY() - fly.y),2))
				< Math.pow(pro.getR() + fly.r,2) ){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean eggFireCollision(Egg egg, Fire fir){
		
		//if egg is hatching, don't count as collision (return false)
		if(egg.isHatching)return false;
		
		if( (Math.pow((fir.getX() - egg.getX()), 2) 
				+ Math.pow((fir.getY() - egg.getY()),2))
				< Math.pow((fir.getR() + egg.getR()),2) ){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean eggPlatformCollision(Egg egg, Platform plat){
		if(((egg.y + egg.r) > plat.y) && (egg.y < plat.y)){
			if((egg.x > plat.x) && ((egg.x + egg.r) < (plat.x + plat.length))){
				return true;				
			}			
		}
		
		return false;
	}
	
	

}
