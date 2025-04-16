// Redirect to login if not authenticated
if (!localStorage.getItem('loggedIn') && window.location.pathname !== '/login.html') {
    window.location.href = 'login.html';
}

// Toggle between login and register forms
function toggleForm(form) {
    document.getElementById('register-box').classList.remove('active');
    document.getElementById('login-box').classList.remove('active');
    document.getElementById(`${form}-box`).classList.add('active');
}
// Register Function
function register() {
    const name = document.getElementById('register-name').value;
    const email = document.getElementById('register-email').value;
    const password = document.getElementById('register-password').value;

    if (name && email && password) {
        // Prepare the request body
        const requestBody = {
            username: name,
            email: email,
            password: password
        };

        // Call the API using fetch
        fetch('http://192.168.0.131:8080/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody)  // ✅ Fixed: Using correct variable
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Registration failed');
            }
            return response.text();
        })
        .then(data => {
            console.log('Registration successful:', data);
            alert('Registration successful! Please log in.');
            toggleForm('login');  // ✅ Redirect to login form
        })
        .catch(error => {
            console.error('Error during registration:', error);
            alert('Registration failed. Try again.');
        });
    } else {
        alert('Please fill in all fields.');
    }
} ;


// Login Function
function login() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    if (username && password) {
        // Prepare the request body
        const requestBody = {
            username: username,
            password: password
        };

        // Call the API using fetch
        fetch('http://192.168.0.131:8080/auth/login', {  // Correct URL
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody)
        })
        .then(response => {
            if (!response.ok) {
                // Handle non-200 responses
                throw new Error('Login failed, please try again.');
            }
            return response.json();
        })
        .then(data => {
            if (data != null) {
                alert('Login successful! Welcome.');
                localStorage.setItem('user', JSON.stringify({ username, password }));
                localStorage.setItem('loggedIn', 'true');  // ✅ Set login status
                window.location.href = 'dashboard.html';   // ✅ Redirect to dashboard
            } else {
                alert('Login failed: ' + data);
            }
        })
        
    
        .catch(error => {
            console.error('Error during login:', error);
            alert('Login failed. Please try again later.');
        });        
    } else {
        alert('Please fill both email and password fields.');
    }
}

 // If already logged in, go straight to dashboard
 //if (localStorage.getItem('loggedIn') === 'true') {
   // window.location.href = 'dashboard.html';
//}

// Logout Function
function logout() {
    localStorage.removeItem('loggedIn');
    window.location.href = 'login.html';
}

// Notes Handling
let notes = JSON.parse(localStorage.getItem('notes')) || [];
let selectedNoteId = null;

// Render Notes Function
function renderNotes(filter = "") {
    const notesList = document.getElementById('notes-list');

    // Check if the element exists before modifying it
    if (notesList) {
        notesList.innerHTML = ''; // Clear the current list

        // Filter and display notes
        notes.forEach((note, index) => {
            if (note.title.toLowerCase().includes(filter.toLowerCase())) {
                const li = document.createElement('li');
                li.textContent = note.title;
                
                // When a note is clicked, select it
                li.onclick = () => selectNote(index);

                // Append the note to the list
                notesList.appendChild(li);
            }
        });
    } 
}

// Function to select a note (you can implement the functionality as needed)
function selectNote(index) {
    selectedNoteId = index;
    console.log('Selected note:', notes[index]);
    // You can add additional actions when a note is selected, like opening it for editing.
}


function selectNote(index) {
    selectedNoteId = index;
    document.getElementById('note-title').textContent = notes[index].title;
    document.getElementById('note-content').value = notes[index].content;
}

// Add Note
document.getElementById('add-note')?.addEventListener('click', () => {
    const newNote = { title: `Note ${notes.length + 1}`, content: '' };
    notes.push(newNote);
    localStorage.setItem('notes', JSON.stringify(notes));
    renderNotes();
});

// Delete Note
document.getElementById('delete-note')?.addEventListener('click', () => {
    if (selectedNoteId !== null) {
        notes.splice(selectedNoteId, 1);
        localStorage.setItem('notes', JSON.stringify(notes));
        selectedNoteId = null;
        document.getElementById('note-title').textContent = 'Select a Note';
        document.getElementById('note-content').value = '';
        renderNotes();
    }
});

// Update Note
document.getElementById('update-note')?.addEventListener('click', () => {
    if (selectedNoteId !== null) {
        notes[selectedNoteId].content = document.getElementById('note-content').value;
        localStorage.setItem('notes', JSON.stringify(notes));
    }
});

// Search Notes
document.getElementById('search-note')?.addEventListener('input', (e) => {
    renderNotes(e.target.value);
});

// Initial Render
renderNotes();
