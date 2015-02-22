
function containsBlankSpace(str)
{
	for(i=0;i<str.length;i++)
	{
		char = str.charAt(0);	
		
		if(char==' ' || char=='\t')
		{
			return true;
		}
	} 
	
	return false;

}