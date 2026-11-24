# Exam Result Management System

A console-based Java application to add, display, search and sort student exam results, with data saved in a file.
Developed as the **DSA project (PS No. 48)** at Rungta International Skills University.

| | |
|---|---|
| **Student** | Supriya Kumari |
| **ERP ID** | RU-25-11495 |
| **Subject** | Data Structures and Algorithms (DSA) |
| **Guide** | Miss Naina Singh |
| **Programme** | B.Tech Computer Science & Engineering (AI & ML) in association with Google |
| **University** | Rungta International Skills University, Bhilai, Chhattisgarh |
| **Session** | 2026-27 |

## Problem Statement
Develop a console application that manages exam results: add student results with marks in three subjects, display all results, search a result by ID and sort results by percentage (high to low). Data is stored in a file.

**Rules:** each subject is out of 100, the pass percentage is 40, and the first generated result ID is `ER101`.

## Features
- **Add Result** - validates marks (0-100), auto-generates the ID (ER101, ER102, ...), calculates total, percentage and Pass/Fail
- **Display All** - shows every stored result
- **Search by ID** - manual Linear Search
- **Sort by Percentage** - manual Bubble Sort, highest first (no built-in sort or search methods used)
- **File handling** - records are loaded from `results.txt` at start and saved on exit (and after every add)

## Concepts Used
Java, ArrayList, Sorting, Searching, Methods, File Handling.

## Project Structure
```
Exam-Result-Management-System/
├── src/
│   └── ExamResultManagement.java     # complete source code (single file)
├── docs/
│   ├── Supriya_Kumari_Synopsis.docx  # project synopsis
│   └── Exam_Result_Management_System.pptx   # 10-slide presentation
├── .gitignore
└── README.md
```

## How to Run
**Requirement:** JDK 11 or higher (JDK 21 recommended).

```bash
cd src
javac ExamResultManagement.java
java ExamResultManagement
```

In VS Code you can also open `ExamResultManagement.java` and click **Run** (Extension Pack for Java required).
The file `results.txt` is created automatically in the folder where the program is run.

## Menu
```
1. Add Result
2. Display All
3. Search by ID
4. Sort by Percentage (high to low)
5. Exit
```

## Sample Output
```
Menu option: 1
Name: Kiran Patel
Marks: 80 70 90
Result added. ID: ER101 | Kiran Patel | Total: 240/300 | Percentage: 80.00 | Result: Pass

Menu option: 1
Name: Mitali Roy
Marks: 35 30 38
Result added. ID: ER102 | Mitali Roy | Total: 103/300 | Percentage: 34.33 | Result: Fail

Menu option: 4
---- Sorted by Percentage (High to Low) ----
1. ER101 | Kiran Patel | 80.00 | Pass
2. ER102 | Mitali Roy | 34.33 | Fail

Menu option: 3
Enter Result ID: ER102
Found: ER102 | Mitali Roy | Total: 103/300 | Percentage: 34.33 | Result: Fail
```

## Test Cases
| # | Input | Expected Output |
|---|-------|-----------------|
| 1 Normal | Kiran Patel, marks 80 70 90 | ER101, 240/300, 80.00, Pass |
| 2 Boundary | Lalit Soni, marks 40 40 40 | 120/300, 40.00, Pass |
| 3 Invalid | Kiran Patel, marks 80 105 90 | Invalid marks. Each mark must be between 0 and 100. |
| 4 Different | Add Mitali Roy 35 30 38, sort, search ER102 | 34.33, Fail; sorted list and search match |

> Note: the ID continues from the highest ID saved in `results.txt`. Delete `results.txt` before a test run to start again from `ER101`.

## Time Complexity
| Operation | Complexity |
|-----------|-----------|
| Add result | O(1) (saving to file: O(n)) |
| Display all | O(n) |
| Linear Search by ID | O(n) |
| Bubble Sort by percentage | O(n²) worst/average |
| Load from file | O(n) |

## Future Scope
- Marks for more subjects and grade calculation
- Database instead of a text file
- Faster sorting (Merge Sort / Quick Sort) and Binary Search by ID
- Update and delete options
- GUI or web interface and report export

## Documents
- [Project Synopsis](docs/Supriya_Kumari_Synopsis.docx)
- [Presentation](docs/Exam_Result_Management_System.pptx)

## Author
**Supriya Kumari** - ERP ID: RU-25-11495
