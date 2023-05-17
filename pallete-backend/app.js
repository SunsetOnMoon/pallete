const express = require('express');
const mysql = require('mysql2');
const fs = require('fs');
const bodyParser = require('body-parser');
const path = require('path');
const multer = require('multer');

const connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    database: "palletedb",
    password: "Creyc0401"
});

connection.connect((err) => {
    if (err) {
        console.error("Ошибка подключения к базе данных: ", err);
    } else {
        console.log('Подключено к базе данных');
    }
})

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'pictures/');
    },
    filename: (req, file, cb) => {
        const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1E9);
        const fileExtension = path.extname(file.originalname);
        cb(null, uniqueSuffix + fileExtension);
    }
});

const upload = multer({storage});

app = express();
app.use(express.static(__dirname + "/pictures"));
app.use(bodyParser.json())


//----------------------------------------USERS-------------------------------------------------------------------------
//get all users
app.get('/api/users', (req, res) => {
    connection.query('SELECT * FROM users', (err, results) => {
        if (err) {
            console.error('Ошибка при выполнении запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.json(results);
        }
    });
});

//get user by id
app.get('/api/users/:id', (req, res) => {
    const userId = req.params.id;
    connection.query('SELECT * FROM users WHERE id = ?', [userId], (err, results) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            if (results.length === 0) {
                res.status(404).send('Пользователь не найден');
            } else {
                res.json(results[0]);
            }
        }
    });
});

// Add new user
app.post('/api/users', (req, res) => {
    const {name, description, subscribers, subscriptions, password} = req.body;
    connection.query('INSERT INTO users (name, description, subscribers, subscriptions, password) VALUES (?, ?, ?, ?, ?)', [name, description, subscribers, subscriptions, password], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            const insertedUserId = result.insertId;
            res.json({id: insertedUserId, name, description, subscribers, subscriptions, password});
        }
    });
});


//delete user by id
app.delete('/api/users/:id', (req, res) => {
    const userId = req.params.id;
    connection.query('DELETE FROM users WHERE id = ?', [userId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.sendStatus(200);
        }
    });
});

//update user by id
app.put('/api/users/:id', (req, res) => {
    const userId = req.params.id;
    const {name, description, subscribers, subscriptions, password} = req.body;
    connection.query('UPDATE users SET name = ?, description = ?, subscribers = ?, subscriptions = ?, password = ? WHERE id = ?', [name, description, subscribers, subscriptions, password, userId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.sendStatus(200);
        }
    });
});

//-------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------POSTS-------------------------------------------------------------------------
//get all posts
app.get('/api/posts', (req, res) => {
    connection.query('SELECT * FROM posts', (err, results) => {
        if (err) {
            console.error('Ошибка при выполнении запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.json(results);
        }
    });
});

//get user by id
app.get('/api/posts/:id', (req, res) => {
    const postId = req.params.id;
    connection.query('SELECT * FROM posts WHERE id = ?', [postId], (err, results) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            if (results.length === 0) {
                res.status(404).send('Пользователь не найден');
            } else {
                res.json(results[0]);
            }
        }
    });
});

// Add new post
app.post('/api/posts', upload.single('image'), (req, res) => {
    const {title, description, authorId, ideaId, topicId} = req.body;
    const rate = 0
    const imagePath = req.file.path;
    connection.query('INSERT INTO posts (title, description, authorId, ideaId, topicId, rate, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)', [title, description, authorId, ideaId, topicId, rate, imagePath], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            const insertedPostId = result.insertId;
            res.json({id: insertedPostId, title, description, authorId, ideaId, topicId, rate, imagePath});
        }
    });
});


//delete post by id
app.delete('/api/posts/:id', (req, res) => {
    const postId = req.params.id;
    connection.query('SELECT imagePath FROM posts WHERE id = ?', [postId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            if (result.length === 0) {
                res.status(404).send('Пост не найден');
            } else {
                const imagePath = result[0].imagePath;

                connection.query('DELETE FROM posts WHERE id = ?', [postId], (err) => {
                    if (err) {
                        console.error('Ошибка выполнения запроса:', err);
                        res.status(500).send('Ошибка сервера');
                    } else {
                        fs.unlink(imagePath, (err) => {
                            if (err) {
                                console.error('Ошибка удаления файла:', err);
                                res.status(500).send('Ошибка сервера');
                            } else {
                                res.sendStatus(200);
                            }
                        })
                    }
                })
            }
        }
    });
});

//update post by id
app.put('/api/posts/:id', (req, res) => {
    const postId = req.params.id;
    const {title, description, authorId, ideaId, topicId, rate, imagePath} = req.body;
    connection.query('UPDATE posts SET title = ?, description = ?, authorId = ?, ideaId = ?, topicId = ?, rate = ?, imagePath = ? WHERE id = ?', [title, description, authorId, ideaId, topicId, rate, imagePath, postId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.sendStatus(200);
        }
    });
});

//-------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------IDEAS-------------------------------------------------------------------------
//get all ideas
app.get('/api/ideas', (req, res) => {
    connection.query('SELECT * FROM ideas', (err, results) => {
        if (err) {
            console.error('Ошибка при выполнении запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.json(results);
        }
    });
});

//get idea by id
app.get('/api/ideas/:id', (req, res) => {
    const ideaId = req.params.id;
    connection.query('SELECT * FROM ideas WHERE id = ?', [ideaId], (err, results) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            if (results.length === 0) {
                res.status(404).send('Пользователь не найден');
            } else {
                res.json(results[0]);
            }
        }
    });
});

// Generate new idea
app.post('/api/ideas', (req, res) => {
    const {name, description, topicId, userId} = req.body;
    connection.query('INSERT INTO ideas (name, description, topicId, userId) VALUES (?, ?, ?, ?)', [name, description, topicId, userId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            const insertedIdeaId = result.insertId;
            res.json({id: insertedIdeaId, name, description, topicId, userId});
        }
    });
});


//delete idea by id
app.delete('/api/ideas/:id', (req, res) => {
    const ideaId = req.params.id;
    connection.query('DELETE FROM ideas WHERE id = ?', [ideaId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.sendStatus(200);
        }
    });
});

//update idea by id
// app.put('/api/ideas/:id', (req, res) => {
//     const ideaId = req.params.id;
//     const {name, description, topicId, userId} = req.body;
//     connection.query('UPDATE ideas SET name = ?, description = ?, topicId = ?, userId = ? WHERE id = ?', [name, description, topicId, userId, ideaId], (err, result) => {
//         if (err) {
//             console.error('Ошибка выполнения запроса:', err);
//             res.status(500).send('Ошибка сервера');
//         } else {
//             res.sendStatus(200);
//         }
//     });
// });

//-------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------COMMENTS-------------------------------------------------------------------------
//get all comments
app.get('/api/comments', (req, res) => {
    connection.query('SELECT * FROM comments', (err, results) => {
        if (err) {
            console.error('Ошибка при выполнении запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.json(results);
        }
    });
});

//get comment by id
app.get('/api/comments/:id', (req, res) => {
    const commentId = req.params.id;
    connection.query('SELECT * FROM ideas WHERE id = ?', [commentId], (err, results) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            if (results.length === 0) {
                res.status(404).send('Пользователь не найден');
            } else {
                res.json(results[0]);
            }
        }
    });
});

//get all comments by postId
app.get('/api/comments_by_post')

// Add new comment
app.post('/api/comments', (req, res) => {
    const {content, authorId, postId} = req.body;
    connection.query('INSERT INTO comments (content, authorId, postId) VALUES (?, ?, ?)', [content, authorId, postId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            const insertedCommentId = result.insertId;
            res.json({id: insertedCommentId, content, authorId, postId});
        }
    });
});


//delete comment by id
app.delete('/api/comments/:id', (req, res) => {
    const commentId = req.params.id;
    connection.query('DELETE FROM comments WHERE id = ?', [commentId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.sendStatus(200);
        }
    });
});

//update comment by id
app.put('/api/ideas/:id', (req, res) => {
    const commentId = req.params.id;
    const {content, authorId, postId} = req.body;
    connection.query('UPDATE comments SET content = ?, authorId = ?, postId = ? WHERE id = ?', [content, authorId, postId, commentId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.sendStatus(200);
        }
    });
});

//-------------------------------------------------------------------------------------------------------------------------------------

app.listen(3000, () => console.log('Server started on port 3000...'));