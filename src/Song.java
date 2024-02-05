 class Song {
        int id;
        String name;
        String singer;
        String duration;
        String genre;
        String path;

        public Song(int id, String name, String singer, String duration, String genre, String path) {
            this.id = id;
            this.name = name;
            this.singer = singer;
            this.duration = duration;
            this.genre = genre;
            this.path = path;
        }

        @Override
        public String toString() {
            return "Song{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", singer='" + singer + '\'' +
                    ", duration='" + duration + '\'' +
                    ", genre='" + genre + '\'' +
                    ", path='" + path + '\'' +
                    '}';
        }
    }
