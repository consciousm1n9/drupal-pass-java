# drupal-pass-java
Drupal password processing in java. Works with Drupal v7, 8, 9.  Change password directly in data base.

Guide
1. Clone, download or open jdoodle online test(https://www.jdoodle.com/a/4CxF). 
2. Change value of 'passwordToCrypt' string variable with pass that you want to change. Save, execute and copy value of result.
3. Update user password columnn in table in db. This column changes depending on the version of Drupal. For Drupal 9 the user password is on 'users_field_data.pass'. Save.
4. NECESARY ONLY FOR DRUPALv9. Delete records in 'cache_entity' table where 'cid' column starts with "value:user*". Save.
5. Log in using password update in last steps and that's it.

![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/67773113/177851342-9041de25-a145-4aa1-a0eb-4d9b42443aa9.gif)

Clear explanation for Drupal password processing:
https://stackoverflow.com/questions/5031662/what-is-drupals-default-password-encryption-method/5031742

Test online:
https://www.jdoodle.com/a/4CxF

Drupal Password procesing file: https://api.drupal.org/api/drupal/core%21lib%21Drupal%21Core%21Password%21PhpassHashedPassword.php/9.0.x
