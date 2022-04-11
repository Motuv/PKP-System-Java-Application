public enum TrainState {
    READY {
        @Override
        public String toString() {
            return "READY";
        }
    },
    ONTIME {
        @Override
        public String toString() {
            return "ONTIME";
        }
    },
    DELAYED {
        @Override
        public String toString() {
            return "DELAYED";
        }
    },
    ENDED {
        @Override
        public String toString() {
            return "ENDED";
        }
    }
}
