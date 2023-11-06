# mobile-redesign

This repo provides a JSON-based greeting and events calendar. It dynamically generates a greeting based on the time of day and serves event data in JSON format.

Reached by visiting: https://itdocs.csun.edu/mobile-redesign

# Instructions to Run 
The code uses Frozen-Flask application in Python to run the file in a static environment. 

- Installations required
    1. Python
        - Download Python from the official site: https://www.python.org/downloads/ 
        - Open _cmd_ on Windows or _terminal_ on macOS. 
        - Check for right installation by typing the following commands. 
            * On Windows: ```python --versioin```
            * On Unix, Linux, macOS: ```python3 --version```
    1. Requests module
        - In _cmd_ or _terminal_, type the following command: ```pip install requests```
    1. icalendar module 
        - In _cmd_ or _terminal_, type the following command: ```pip install icalendar```
    1. flask 
        - In _cmd_ or _terminal_, type the following command: ```pip install flask```
    1. frozen-flask 
        - In _cmd_ or _terminal_, type the following command: ```pip install frozen-flask```

- Creating a virutal environment
    - On _cmd_ or _terminal_ ,enter the following command
        ```python -m venv venv```

- Setting up the virtual environment 
    - For Windows: 
        ```venv/Scripts/activate```
    - For macOS: 
        ```source venv/bin/activate```
