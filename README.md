**First Refactoring - Variable Renaming for Clarity**

- For the first round of refactoring, I went through the code and kept an eye out for 
any variables that were named poorly. Most of the variables were actually named relatively okay, but some were named poorly,
such as when declaring an `ActionEvent`, like so `ActionEvent e`. 'e' is a poor variable name choice as it does not provide
 any hint to the person reading the code as to its purpose or relevance.
 
- 2, I removed a redundant constructor from the Employee class. Redundant code is no use as it adds extra unnecessary 
ambiguity and complexity to code without providing any extra value. Will also add extra time to the compilation process,
minuscule as it may be.

- 3, I extracted the `.getText().trim()` method chain into a single method which returned a String as it was being used in 
quite a few places. Having it in one single method makes teh code more concise and readable 
`public String getTextAndTrim(JTextField jTextField){
   return jTextField.getText().trim();
}`

- 4, Rather than calling an anonymous `Color` class for every time the application needs to set a new colour, I just 
created a new color instance as a global variable in the EmployeeDetails class `private Color incorrectInput = 
new Color(255, 150, 150);` and called the incorrectInput object to set the colour