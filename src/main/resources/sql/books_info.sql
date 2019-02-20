CREATE TABLE IF NOT EXISTS categories(
                   id BIGSERIAL PRIMARY KEY,
                   category VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS authors(
                    id BIGSERIAL PRIMARY KEY ,
                    full_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS books(
                    id BIGSERIAL PRIMARY KEY ,
                    title VARCHAR(255) NOT NULL,
                    language VARCHAR(255) NOT NULL ,
                    page_count INTEGER,
                    image_ref VARCHAR(1000),
                    description TEXT,
                    rating INTEGER DEFAULT 0,
                    isbn_10 VARCHAR(255),
                    isbn_13 VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS book_authors (
                    book_id BIGINT REFERENCES books(id) ON UPDATE CASCADE ON DELETE CASCADE ,
                    author_id BIGINT REFERENCES authors(id) ON UPDATE CASCADE ON DELETE CASCADE ,
                    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE IF NOT EXISTS book_categories (
                    book_id BIGINT REFERENCES books(id) ON UPDATE CASCADE ON DELETE CASCADE ,
                    category_id BIGINT REFERENCES categories(id) ON UPDATE CASCADE ON DELETE CASCADE,
                    PRIMARY KEY(book_id, category_id)
);