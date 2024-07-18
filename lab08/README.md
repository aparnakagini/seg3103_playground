Screenshots:
<img width="1359" alt="PNG image" src="https://github.com/user-attachments/assets/5206e8f3-07bd-4b64-882e-c6db2650d144">
<img width="791" alt="PNG image" src="https://github.com/user-attachments/assets/ede8360c-368e-4a77-abe9-d2e39bbb07ab">
<img width="814" alt="PNG image" src="https://github.com/user-attachments/assets/846b2f06-f88d-4546-bcff-1f64307f2611">
<img width="850" alt="PNG image" src="https://github.com/user-attachments/assets/e19643df-8efb-42fe-b77a-2df13c9b65f0">

In your own words explain what the various mutations are doing and what the report tells you:

The PIT Test Coverage Report indicates that all the lines of code in the 'calculator' class were tested (100% line coverage) and that all the potential mutation points in the code were successfully tested (100% mutation coverage). Mutation testing involves making small changes to the code to ensure that the tests can detect errors. The report shows that there were five lines of code, and all five were tested, along with eight mutation points, all of which were covered.

Explain what it means for a mutant to be killed:

In mutation testing, a mutant is a version of the code that has been deliberately altered to introduce a small error or change, such as modifying an operator or a variable. A mutant is considered "killed" if the test suite detects the change and fails as a result, indicating that the tests are effective in identifying errors. If the tests pass despite the mutation, the mutant is considered "alive," suggesting that the test suite is not thorough enough to catch that specific error.
