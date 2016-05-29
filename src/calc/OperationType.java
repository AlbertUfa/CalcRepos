package calc;
//enum который содержит все типы наших операций
//и для каждого вида операции сохраняет его символ
//Чтобы наглядно выводить списки операций пользователя 
public enum OperationType {
	
	ADD("+"),
	SUBTRACT("-"),
	MULTIPLY("*"),
	DIVIDE("/");
    //создаем строковую перменную 
	private String stringValue;
	
	private OperationType(String stringvalue){
		this.stringValue = stringvalue;
	}
	
	public String getStringValue() {
		return stringValue;
	}

}
