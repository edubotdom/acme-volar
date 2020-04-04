Feature: Traditional Login 
   I can do login in the system with my user name and password.
  
  Scenario: Successful login as admin1 (Positive)
    Given I am not logged in the system
    When I do login as user "admin1"
    Then "admin1" appears as the current user
    
  Scenario: Login fail admin1 (Negative)
  	Given I am not logged in the system
    When I try to do login as user "admin1" with an invalid password
    Then the login form is shown again
    
   Scenario: Successful login as client1 (Positive)
    Given I am not logged in the system
    When I do login as user "client1"
    Then "client1" appears as the current user
    
  Scenario: Login fail client1 (Negative)
  	Given I am not logged in the system
    When I try to do login as user "client1" with an invalid password
    Then the login form is shown again
    
  Scenario: Successful login as airline1 (Positive)
    Given I am not logged in the system
    When I do login as user "airline1"
    Then "airline1" appears as the current user
    
  Scenario: Login fail airline1 (Negative)
  	Given I am not logged in the system
    When I try to do login as user "airline1" with an invalid password
    Then the login form is shown again
  