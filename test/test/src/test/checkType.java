package test;

public enum checkType {
	XS_FIRST_CREDIT_AUDIT("1","信审初审"),
	XS_SECOND_CREDIT_AUDIT("2","信审复审"),
	XS_THRED_CREDIT_AUDIT("3","信审终审"),
	XS_FINAL_CREDIT_AUDIT("4","信审高级终审"),
	FY_FIRST_CREDIT_AUDIT("5","复议初审"),
	FY_SECOND_CREDIT_AUDIT("6","复议复审"),
	FY_FINAL_CREDIT_AUDIT("7","复议终审"),
	FRAUDS_CREDIT_AUDIT("8","反欺诈判定"),
	APPLY_ENGINE_AUDIT("9","进件引擎"),
	FRAUDS_ENGINE_AUDIT("10","反欺诈引擎判定"),
	SYS_AUTO_AUDIT("11","系统判定");
	
	private checkType(String name,String code){
		this.code=code;
		this.name=name;
	}
	private String name;
	private String code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
