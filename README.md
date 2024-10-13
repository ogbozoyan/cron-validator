# Spring Cron Expression Validator

This tool allows users to validate and preview the next few execution times of a given cron expression. It provides
immediate feedback on whether a cron expression is valid and shows the next five occurrences based on the current system
time.

## Usage

To use the Spring Cron Expression Validator, follow these steps:

1. Run the application.
2. When prompted, enter a cron expression or type `exit` to quit the application.

## Example Log

```plaintext
Enter cron expression (or type 'exit' to quit):  0  0/5 * * * *
Cron Expression:  0  0/5 * * * *
Next few instances:
2024-02-11  19:10:00
2024-02-11  19:15:00
2024-02-11  19:20:00
2024-02-11  19:25:00
2024-02-11  19:30:00
Enter cron expression (or type 'exit' to quit): e
Invalid cron expression
Enter cron expression (or type 'exit' to quit): 0
Invalid cron expression
Enter cron expression (or type 'exit' to quit): exit
```

## Supported Cron Syntax

The validator supports the standard cron syntax with six single space-separated time and date fields. Here's a brief
overview of the syntax:

- Seconds:  0-59
- Minutes:  0-59
- Hours:  0-23
- Day of Month:  1-31
- Month:  1-12 (or JAN-DEC)
- Day of Week:  0-7 (0 or 7 is Sunday, or MON-SUN)

Additionally, the validator supports the use of asterisks (`*`), ranges (e.g., `1-5`), and steps (e.g., `*/5`). English
names for months and days of the week are also accepted.

## Notes

- To run Ollama with debugging use `ollama serve`
- Invalid cron expressions will result in an error message indicating that the expression is invalid.
- The application will display the next five execution times for the valid cron expressions entered.

## TODO

- add Native query support like 'give me every 1th november' and llm will generate cron and by validator check result
  and give output
 