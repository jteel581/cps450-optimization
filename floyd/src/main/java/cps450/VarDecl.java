package cps450;

public class VarDecl extends Declaration {
	
	String name;
	Integer offSet;
	public VarDecl(Type type, String name) {
		super(type);
		this.type = type;
		this.name = name;
		this.varDecl = true;
		
	}
	public void setOffSet(Integer newOffSet)
	{
		this.offSet = newOffSet;
	}
	public Integer getOffSet()
	{
		return this.offSet;
	}

}
