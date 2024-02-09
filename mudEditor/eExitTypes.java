package mudEditor;

public enum eExitTypes {
	eNORTH,     //0
	eSOUTH,     //1
	eEAST,      //2
	eWEST,      //3
	eNORTHEAST, //4
	eNORTHWEST, //5
	eSOUTHWEST, //6
	eSOUTHEAST, //7
	eUP,        //8
	eDOWN,      //9
	eSOMEWHERE; //10
	
	
	public eExitTypes reverse() {
		switch(this) {
		case eNORTH:
			return eSOUTH;
		case eSOUTH:
			return eNORTH;
		case eEAST:
			return eWEST;
		case eWEST:
			return eEAST;
		case eNORTHEAST:
			return eSOUTHWEST;
		case eNORTHWEST:
			return eSOUTHEAST;
		case eSOUTHWEST:
			return eNORTHEAST;
		case eSOUTHEAST:
			return eNORTHWEST;
		case eUP:
			return eDOWN;
		case eDOWN:
			return eUP;
			default:
				return eSOMEWHERE;
		}
	}
			
};
