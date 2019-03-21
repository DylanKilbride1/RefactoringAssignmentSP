**First Refactoring - Variable Renaming for Clarity**

- For the first round of refactoring (Branch: RASP-1-Rename-Variables), I went through the code and kept an eye out for 
any variables that were named poorly. Most of the variables were actually named relatively okay, but some were named poorly,
such as when declaring an `ActionEvent`, like so `ActionEvent e`. 'e' is a poor variable name choice as it does not provide
 any hint to the person reading the code as to its purpose or relevance.
 
