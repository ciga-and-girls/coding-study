package kyungrin.unsolved;

public class PGM_60059 {
  static int Nsize;
  static int Msize;
  static int[][] KEY;
  static int[][] LOCK;
  public boolean solution(int[][] key, int[][] lock) {
    Nsize = lock.length;
    Msize = key.length;
    KEY = key;
    LOCK = lock;

    // 우선 KEY의 돌기(1)가 LOCK의 홈(0)보다 작으면 무조건 false
    for(int r = 0; r < 4; r++) {
      // 1. key 회전
      // 90도 회전: r => c, c => s -1 -r
      int[][] newKey = rotation();

      // 큰 배열 생성
      int[][] bigLock = new int[3*Nsize][3*Nsize];
      // 자물쇠 중앙에 붙이기
      for(int i = 0; i < Nsize; i++){
        for(int j = 0; j < Nsize; j++){
          bigLock[i+Nsize][j+Nsize] = LOCK[i][j];
        }
      }

      // key의 시작 위치 sr, sc 잡기
      for(int sr = 0; sr < Nsize*2; sr++){
        for(int sc = 0; sc < Nsize*2; sc++){

          // 시작 위치 기준으로 ++
          for(int i = 0; i < Msize; i++){
            for(int j = 0; j < Msize; j++){
              bigLock[sr + i][sc + j] += newKey[i][j];
            }
          }

          // N~2*N-1 까지 전부 1로 채워져있는지 확인 -> true
          boolean isOpen = true;
          for(int i = Nsize; i < 2*Nsize; i++){
            for(int j = Nsize; j < 2*Nsize; j++){
              if(bigLock[i][j] != 1) {
                isOpen = false;
                break;
              }
            }
          }
          if(isOpen) return true;

          // 시작 위치 기준으로 다시 --
          for(int i = 0; i < Msize; i++){
            for(int j = 0; j < Msize; j++){
              bigLock[sr + i][sc + j] -= key[i][j];
            }
          }
        }
      }
    }
    return false;
  }

  private static int[][] rotation(){
    int[][] newKey = new int[Msize][Msize];

    for(int r = 0; r < Msize; r++){
      for(int c = 0; c < Msize; c++) {
        newKey[Msize -1 -r][c] = KEY[c][r];
      }
    }

    for(int r = 0; r < Msize; r++){
      for(int c = 0; c < Msize; c++) {
        KEY[r][c] = newKey[r][c];
      }
    }

    return newKey;
  }
}
