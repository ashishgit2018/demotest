Feature: Test DB

  @db
  Scenario: DB test scenario
    Given Execute the update query "UPDATE employee SET EmpAge = '22' WHERE (EmpId = '3')"
    And Execute the update query "commit"
    Then Validate the select query "select empage,empid from <PROPVALUE(emp.tablename)> where empid = '3'"
      | empid | empage |
      | 3     | 22     |
    Given Validate the select query "select * from employee where empid in ('1','3')"
      | EMPAGE | empid | empname     | emplocation |
      | 28     | 1     | rahUl Goyal | BanGaLOre   |
      | 22     | 3     | testUser    |             |
    And Execute the select query "select * from employee where empid in ('1','3')"