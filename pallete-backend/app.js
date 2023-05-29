const express = require('express');
const mysql = require('mysql2');
const fs = require('fs');
const bodyParser = require('body-parser');
const path = require('path');
const multer = require('multer');
const ImageKit = require('imagekit');

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
        cb(null, 'pictures');
    },
    filename: (req, file, cb) => {
        const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1E9);
        const fileExtension = path.extname(file.originalname);
        cb(null, file.fieldname + uniqueSuffix + fileExtension);
    }
});

const imageKit = new ImageKit({
    publicKey: "public_NK8osLpngLeDnSnYZSCBa/ksnmg=",
    privateKey: "private_pEtU4cRBoC6xJl0gwqILMKfZ0Po=",
    urlEndpoint: "https://ik.imagekit.io/2mv7ms7yh"
})

const upload = multer({ storage: storage })
// const upload = multer({dest: "/pictures"});

const app = express();
// app.use(express.static(__dirname + "/pictures"));
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
    connection.query('SELECT * FROM users WHERE userId = ?', [userId], (err, results) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            if (results.length === 0) {
                res.status(404).send('Пользователь не найден');
            } else {
                console.log(results[0]);
                res.json(results[0]);
            }
        }
    });
});

// Add new user
app.post('/api/users/register', (req, res) => {
    const {name, email, password} = req.body;
    connection.query('INSERT INTO users (name, description, subscribers, subscriptions, password, email) VALUES (?, ?, ?, ?, ?, ?)', [name, "", 0, 0, password, email], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            const insertedUserId = result.insertId;
            console.log(result.insertId);
            res.json({userId: insertedUserId, name: name, description: '', subscribers: '0', subscriptions: '0', password: password, email: email});
        }
    });
});


//delete user by id
app.delete('/api/users/:id', (req, res) => {
    const userId = req.params.id;
    connection.query('DELETE FROM users WHERE userId = ?', [userId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.sendStatus(200);
        }
    });
});

app.post('/api/users/valid', (req, res) => {
    const {name, password} = req.body;
    console.log(name, password);
    connection.query('SELECT * FROM users WHERE BINARY name = ? AND password = ?', [name, password], (err, results) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            if (results.length === 0) {
                console.log("User not found");
                res.status(401).json({error: 'Пользователь не найден или неверный пароль'})
            } else {
                console.log(results);
                res.json(results[0]);
            }
        }
    })
})

//update user by id
app.put('/api/users/:id', (req, res) => {
    const userId = req.params.id;
    const {name, description, subscribers, subscriptions, password} = req.body;
    connection.query('UPDATE users SET name = ?, description = ? WHERE userId = ?', [name, description, userId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            console.log(result)
            connection.query('SELECT * FROM users WHERE userId=?', [userId], (err, result) => {
                if (err) {
                    console.error('Ошибка выполнения запроса:', err);
                    res.status(500).send('Ошибка сервера');
                } else {
                    console.log(result)
                    res.json(result[0])
                }
            })
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
    connection.query('SELECT * FROM posts WHERE postId = ?', [postId], (err, results) => {
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

app.get('/api/posts/user/:id', (req, res) => {
    const userId = req.params.id
    connection.query('SELECT * FROM posts WHERE authorId = ?', [userId], (err, results) => {
       if (err) {
           console.error('Ошибка выполнения запроса:', err);
           res.status(500).send('Ошибка сервера')
       } else {
           if (results.length === 0) {
               res.status(404).send('Пользователь не найден');
           } else {
               res.json(results);
           }
       }
    });
});

app.post('/api/posts/search', (req, res) => {
    console.log(req.body)
    const {searchTerm} = req.body;
    console.log(searchTerm);
    const query = 'SELECT * FROM posts WHERE title LIKE ' + '\'' + '%' + searchTerm + '%\''
    console.log(query);
    connection.query(query, (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
        } else {
            console.log(result);
            res.json(result);
        }
    })

})

// Add new post
app.post('/api/posts', upload.single('image'), (req, res) => {
    const {title, description, authorId, ideaId, topicId} = req.body;
    const rate = 0
    var imagePath;
    var file;
    console.log(req.file.path);
    fs.readFile(req.file.path, function (err, data) {
        if (err)
            console.log(err);
        else {
            imageKit.upload({
                file: data,
                fileName: req.file.filename,
                folder: 'pallete_posts'
            }, function (err, response) {
                if (err) {
                    // res.status(500).json({
                    //     status: "failed",
                    //     message: "An error occured during file upload. Please try again."
                    // })
                    console.log("Fail");
                    res.status(200);
                } else {
                    console.log(response.url)
                    imagePath = response.url
                    connection.query('INSERT INTO posts (title, description, authorId, ideaId, topicId, rate, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)', [title, description, authorId, ideaId, topicId, rate, imagePath], (err, result) => {
                        if (err) {
                            console.error('Ошибка выполнения запроса:', err);
                            res.status(500).send('Ошибка сервера');
                        } else {
                            const insertedPostId = result.insertId;
                            res.json({postId: insertedPostId, title, description, authorId, ideaId, topicId, rate, imagePath});
                        }
                    });
                }
            })
        }
    })
});

app.post('/api/posts/filter', (req, res) => {
   const topicId = req.body
   connection.query('SELECT * FROM posts WHERE topicId=?', [topicId], (err, results) => {
       if (err) {
           console.error('Ошибка выполнения запроса:', err);
           res.status(500).send('Ошибка сервера')
       } else {
           res.json(results);
       }
   })
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
    const {title, description, ideaId, topicId} = req.body;
    connection.query('UPDATE posts SET title = ?, description = ?, ideaId = ?, topicId = ? WHERE postId = ?', [title, description, ideaId, topicId, postId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            console.log({result});
            connection.query('SELECT * FROM posts WHERE postId=?', [postId], (err, result) => {
                if (err) {
                    console.error('Ошибка выполнения запроса:', err);
                    res.status(500).send('Ошибка сервера');
                } else {
                    console.log(result)
                    res.json(result[0])
                }
            })
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
            res.json({results});
        }
    });
});

//get idea by id
app.get('/api/ideas/:id', (req, res) => {
    const ideaId = req.params.id;
    connection.query('SELECT * FROM ideas WHERE ideaId = ?', [ideaId], (err, results) => {
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
            res.json({ideaId: insertedIdeaId, name, description, topicId, userId});
        }
    });
});

app.post('/api/colors/', (req, res) => {
    const {ideaId, color} = req.body
    connection.query('INSERT INTO ideacolors (ideaId, color) VALUES (?, ?)', [ideaId, color], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            console.log({result})
            res.sendStatus(200).message('Success');
        }
    })
})


//delete idea by id
app.delete('/api/ideas/:id', (req, res) => {
    const ideaId = req.params.id;
    connection.query('DELETE FROM ideas WHERE ideaId = ?', [ideaId], (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            console.log(result);
            res.sendStatus(200);
        }
    });
});

//get user idea
app.get("/api/ideas/user/:id", (req, res) => {
    const userId = req.params.id;
    connection.query('SELECT * FROM ideas WHERE userId=?', [userId], (err, results) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
        } else {
            console.log(results);
            res.send(results);
        }
    })
})

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
app.get('/api/comments/post/:id', (req, res) => {
    const postId = req.params.id
    connection.query('SELECT * FROM comments WHERE postId=?', [postId], (err, results) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.json(results);
        }
    })
})

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
            res.json({result});
        }
    });
});

//-------------------------------------------------------------------------------------------------------------------------------------
app.get('/api/topics', (req, res) => {
    connection.query('SELECT * FROM topics', (err, result) => {
        if (err) {
            console.error('Ошибка выполнения запроса:', err);
            res.status(500).send('Ошибка сервера');
        } else {
            res.json(result)
        }
    })
})
app.listen(3000, () => console.log('Server started on port 3000...'));